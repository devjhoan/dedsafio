package cc.atenea.dedsafioUtils.utilities.command;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

public class CommandManager implements CommandExecutor {

  private final DedsafioPlugin plugin;
  private final FileConfig languageFile;

  private final Map<String, Entry<Method, Object>> commandMap = new HashMap<>();
  private CommandMap map;

  public CommandManager(DedsafioPlugin plugin) {
    this.plugin = plugin;
    this.languageFile = plugin.getFile("language");

    if (plugin.getServer().getPluginManager() instanceof SimplePluginManager manager) {
      try {
        Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        field.setAccessible(true);
        map = (CommandMap) field.get(manager);
      } catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
    return handleCommand(sender, cmd, label, args);
  }

  public boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
    for (int i = args.length; i >= 0; i--) {
      StringBuilder buffer = new StringBuilder();

      buffer.append(label.toLowerCase());

      for (int x = 0; x < i; x++) {
        buffer.append(".").append(args[x].toLowerCase());
      }

      String cmdLabel = buffer.toString();

      if (commandMap.containsKey(cmdLabel)) {
        Method method = commandMap.get(cmdLabel).getKey();
        Object methodObject = commandMap.get(cmdLabel).getValue();
        Command command = method.getAnnotation(Command.class);

        if (!command.permission().isEmpty() && (!sender.hasPermission(command.permission()))) {
          ChatUtil.sendMessage(sender, languageFile.getString("base-command-message.no-permission"));
          return true;
        }
        if (command.inGameOnly() && !(sender instanceof Player)) {
          ChatUtil.sendMessage(sender, languageFile.getString("base-command-message.no-console"));
          return true;
        }

        try {
          method.invoke(methodObject,
              new CommandArgs(sender, cmd, label, args, cmdLabel.split("\\.").length - 1));
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
          throw new RuntimeException(e.getMessage());
        }
        return true;
      }
    }
    return true;
  }

  public void registerCommands(Object obj) {
    for (Method m : obj.getClass().getMethods()) {
      if (m.getAnnotation(Command.class) != null) {
        Command command = m.getAnnotation(Command.class);

        if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
          throw new RuntimeException("Unable to register command " + m.getName() + ". Unexpected method arguments");
        }

        registerCommand(command, command.name(), m, obj);

        for (String alias : command.aliases()) {
          registerCommand(command, alias, m, obj);
        }
      }
    }
  }

  public void registerCommands(BaseCommand obj, String commandName) {
    for (Method m : obj.getClass().getMethods()) {
      if (m.getAnnotation(Command.class) != null) {
        Command command = m.getAnnotation(Command.class);

        if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
          throw new RuntimeException("Unable to register command " + m.getName() + ". Unexpected method arguments");
        }

        registerCommand(command, commandName, m, obj);

        for (String alias : command.aliases()) {
          registerCommand(command, alias, m, obj);
        }
      }
    }
  }

  public void registerCommands(BaseCommand obj, String commandName, List<String> aliases) {
    for (Method m : obj.getClass().getMethods()) {
      if (m.getAnnotation(Command.class) != null) {
        Command command = m.getAnnotation(Command.class);

        if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
          throw new RuntimeException("Unable to register command " + m.getName() + ". Unexpected method arguments");
        }

        registerCommand(command, commandName, m, obj);

        for (String alias : aliases) {
          registerCommand(command, alias, m, obj);
        }
      }
    }
  }

  public void registerCommand(Command command, String label, Method m, Object obj) {
    String pluginName = plugin.getName();

    commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<>(m, obj));
    commandMap.put(pluginName.toLowerCase() + ':' + label.toLowerCase(), new AbstractMap.SimpleEntry<>(m, obj));

    String cmdLabel = label
        .replace(".", ",")
        .split(",")[0]
        .toLowerCase();

    if (map.getCommand(cmdLabel) == null) {
      org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, this, plugin);
      map.register(pluginName, cmd);
    }

    if (!command.description().equalsIgnoreCase("") && cmdLabel.equals(label)) {
      Objects.requireNonNull(map.getCommand(cmdLabel)).setDescription(command.description());
    }

    if (!command.usage().equalsIgnoreCase("") && cmdLabel.equals(label)) {
      Objects.requireNonNull(map.getCommand(cmdLabel)).setUsage(command.usage());
    }
  }
}