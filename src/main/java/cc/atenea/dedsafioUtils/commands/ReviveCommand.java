package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.ABooleanArgument;
import dev.jorel.commandapi.annotations.arguments.AOfflinePlayerArgument;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@Command("revive")
@Permission("revive")
public class ReviveCommand {
  @Default()
  public static void run(
    CommandSender sender,
    @AOfflinePlayerArgument OfflinePlayer player,
    @ABooleanArgument boolean alertUsers
  ) {
    if (player == null || player.getName() == null) {
      ChatUtil.sendMessage(sender, "&cYou must specify a player");
      return;
    }

    User user = DedsafioPlugin.getInstance().userManager.getUser(player.getUniqueId());
    if (!user.isDead()) {
      ChatUtil.sendMessage(sender, "&c" + player.getName() + " is already alive");
      return;
    }

    Bukkit.getBanList(BanList.Type.PROFILE).pardon(player.getName());
    user.setDead(false);

    if (!alertUsers) {
      user.setAlertRevive(true);
      user.addRevivedTimes();
    }

    ChatUtil.sendMessage(sender, "&aYou revived " + player.getName() + " successfully");
  }
}
