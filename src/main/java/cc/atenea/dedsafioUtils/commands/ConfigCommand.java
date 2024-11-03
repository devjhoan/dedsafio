package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.ATextArgument;
import org.bukkit.command.CommandSender;

@Command("config")
@Permission("config")
public class ConfigCommand {
  @Default()
  public static void help(CommandSender sender) {
    ChatUtil.sendMessage(sender, "&7/config set <key> <value>");
    ChatUtil.sendMessage(sender, "&7/config get <key>");
  }

  @Subcommand("set")
  public static void set(CommandSender sender, @ATextArgument String key, @ATextArgument String value) {
    DedsafioPlugin.getInstance().db.set(key, value);
    DedsafioPlugin.getInstance().db.save();

    ChatUtil.sendMessage(sender, "&7Saved value: " + value);
  }

  @Subcommand("get")
  public static void get(CommandSender sender, @ATextArgument String key) {
    var value = DedsafioPlugin.getInstance().db.get(key);
    ChatUtil.sendMessage(sender, "&7Value: " + value);
  }
}
