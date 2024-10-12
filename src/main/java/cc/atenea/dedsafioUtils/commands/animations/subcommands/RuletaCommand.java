package cc.atenea.dedsafioUtils.commands.animations.subcommands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.command.BaseCommand;
import cc.atenea.dedsafioUtils.utilities.command.Command;
import cc.atenea.dedsafioUtils.utilities.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class RuletaCommand extends BaseCommand {
  private final DedsafioPlugin plugin;
  private final Map<String, String> colors = Map.of(
      "amarilla", "yellow",
      "azul", "blue",
      "morada", "purple",
      "naranja", "orange",
      "roja", "red",
      "rosada", "pink",
      "turqueza", "cyan",
      "verde", "green"
  );

  public RuletaCommand(DedsafioPlugin plugin) {
    this.plugin = plugin;
  }

  @Command(name = "animation.ruleta", permission = "dedsafio.command.animation.ruleta", inGameOnly = false)
  @Override
  public void onCommand(CommandArgs command) {
    CommandSender sender = command.getSender();
    String[] args = command.getArgs();

    if (args.length == 0) {
      ChatUtil.sendMessage(sender, LanguageResource.ANIMATION_COMMAND_MESSAGE_HELP);
      return;
    }

    String color = args[0];
    String target = args.length == 1 ? "all" : args[1];

    if (!colors.containsKey(color)) {
      ChatUtil.sendMessage(sender, "&cInvalid color. Valid colors: " + String.join(", ", colors.keySet()));
      return;
    }

    if (target.equalsIgnoreCase("all")) {
      plugin.sendConsoleCommand("tag @a add anim.ruleta." + colors.get(color));
    } else {
      Player player = Bukkit.getPlayer(target);
      if (player != null) {
        plugin.sendConsoleCommand("tag " + player.getName() + " add anim.ruleta." + color);
      } else {
        ChatUtil.sendMessage(sender, "&cPlayer not found.");
      }
    }

    ChatUtil.sendMessage(sender, "&bRuleta animation sent to " + (target.equalsIgnoreCase("all") ? "all players." : target + "."));
  }


}
