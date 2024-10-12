package cc.atenea.dedsafioUtils;

import cc.atenea.dedsafioUtils.commands.DedsafioCommand;
import cc.atenea.dedsafioUtils.commands.animations.AnimationCommand;
import cc.atenea.dedsafioUtils.commands.animations.subcommands.MuerteCommand;
import cc.atenea.dedsafioUtils.commands.animations.subcommands.RuletaCommand;
import cc.atenea.dedsafioUtils.events.DeathEvent;
import cc.atenea.dedsafioUtils.resources.ResourceManager;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.command.CommandManager;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class DedsafioPlugin extends JavaPlugin {
  private final Map<String, FileConfig> files;
  private ResourceManager resourceManager;

  public DedsafioPlugin() {
    this.files = new HashMap<>();
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    System.out.println(ChatUtil.translate("&c&lDedsafio-Utils enabled"));

    // Register Files
    files.put("language", new FileConfig(this, "language.yml"));

    // Register Managers
    CommandManager commandManager = new CommandManager(this);
    this.resourceManager = new ResourceManager(this);

    // Register Command
    commandManager.registerCommands(new DedsafioCommand(this));
    commandManager.registerCommands(new AnimationCommand());
    commandManager.registerCommands(new RuletaCommand(this));
    commandManager.registerCommands(new MuerteCommand(this));

    // Register Events
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new DeathEvent(this), this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public void onReload() {
    files.values().forEach(FileConfig::reload);
    resourceManager.onRefresh();
  }

  public FileConfig getFile(String name) {
    return files.get(name);
  }

  public void sendConsoleCommand(String command) {
    ConsoleCommandSender sender = getServer().getConsoleSender();
    getServer().dispatchCommand(sender, command);
  }
}
