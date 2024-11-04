package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.ABooleanArgument;
import dev.jorel.commandapi.annotations.arguments.AEntitySelectorArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("soul")
public class SoulCommand {
  @Default
  public static void run(
    CommandSender sender,
    @AEntitySelectorArgument.OnePlayer Player player
  ) {
    var plugin = DedsafioPlugin.getInstance();
    if (player == null) {
      ChatUtil.sendMessage(sender, "&cNo hemos podido encontrar al jugador");
      return;
    }

    User userProfile = plugin.userManager.getUser(player);
    String hasSoulMessage = "&aEl jugador " + player.getName() + " tiene una alma";
    String noSoulMessage = "&cEl jugador " + player.getName() + " no tiene una alma";

    ChatUtil.sendMessage(sender, userProfile.hasSoul() ? hasSoulMessage : noSoulMessage);
  }

  @Subcommand("set")
  @Permission("soul.set")
  public static void set(
    CommandSender sender,
    @AEntitySelectorArgument.OnePlayer Player player,
    @ABooleanArgument boolean soul
  ) {
    var plugin = DedsafioPlugin.getInstance();
    if (player == null) {
      ChatUtil.sendMessage(sender, "&cNo hemos podido encontrar al jugador");
      return;
    }

    User userProfile = plugin.userManager.getUser(player);
    String setSoulTrueMessage = "&aEl jugador " + player.getName() + " ahora tiene alma";
    String setSoulFalseMessage = "&cEl jugador " + player.getName() + " ahora no tiene alma";

    userProfile.setSoul(soul);
    ChatUtil.sendMessage(sender, userProfile.hasSoul() ? setSoulTrueMessage : setSoulFalseMessage);
  }
}
