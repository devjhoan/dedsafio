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

public class MuerteCommand extends BaseCommand {
  private final DedsafioPlugin plugin;

  public MuerteCommand(DedsafioPlugin plugin) {
    this.plugin = plugin;
  }

  @Command(name = "animation.muerte", permission = "dedsafio.command.muerte", inGameOnly = false)
  @Override
  public void onCommand(CommandArgs command) {
    CommandSender sender = command.getSender();
    String[] args = command.getArgs();

    if (args.length == 0) {
      ChatUtil.sendMessage(sender, LanguageResource.ANIMATION_COMMAND_MESSAGE_HELP);
      return;
    }

    String target = args[0];
    if (target.equalsIgnoreCase("all")) {
      plugin.sendConsoleCommand("tag @a add anim.muerte");
    } else {
      Player player = Bukkit.getPlayer(target);
      if (player != null) {
        plugin.sendConsoleCommand("tag " + player.getName() + " add anim.muerte");
      } else {
        ChatUtil.sendMessage(sender, "&cPlayer not found.");
      }
    }

    ChatUtil.sendMessage(sender, "&bMuerte animation sent to " + (target.equalsIgnoreCase("all") ? "all players." : target + "."));
  }
}
