package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.bukkit.command.CommandSender;

@Command("dedsafio")
@Permission("dedsafio")
public class DedsafioCommand {

  @Default()
  public static void run(CommandSender sender) {
    ChatUtil.sendMessage(sender, LanguageResource.DEDSAFIO_COMMAND_MESSAGE_HELP);
  }

  @Subcommand("reload")
  public static void reload(CommandSender sender) {
    DedsafioPlugin.getInstance().onReload();
    ChatUtil.sendMessage(sender, LanguageResource.DEDSAFIO_COMMAND_MESSAGE_RELOAD);
  }

  @Subcommand("version")
  public static void version(CommandSender sender) {
    ChatUtil.sendMessage(sender, "&7Version: &f" + DedsafioPlugin.getInstance().getDescription().getVersion());
  }
}
