package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.command.BaseCommand;
import cc.atenea.dedsafioUtils.utilities.command.Command;
import cc.atenea.dedsafioUtils.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class DedsafioCommand extends BaseCommand {
  private final DedsafioPlugin plugin;

  public DedsafioCommand(DedsafioPlugin plugin) {
    this.plugin = plugin;
  }

  @Command(name = "dedsafio", permission = "dedsafio.command.dedsafio", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            ChatUtil.sendMessage(sender, LanguageResource.DEDSAFIO_COMMAND_MESSAGE_HELP);
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            ChatUtil.sendMessage(sender, LanguageResource.DEDSAFIO_COMMAND_MESSAGE_RELOAD);
            return;
        }

        ChatUtil.sendMessage(sender, "&cpendejo de mierda, ese comando no existe xdddd");
    }
}
