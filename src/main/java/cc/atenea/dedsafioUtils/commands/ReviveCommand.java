package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.ABooleanArgument;
import dev.jorel.commandapi.annotations.arguments.AEntitySelectorArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@Command("revive")
@Permission("revive")
public class ReviveCommand {
  @Default()
  public static void run(
    CommandSender sender,
    @AEntitySelectorArgument.ManyPlayers Collection<Player> players,
    @ABooleanArgument boolean silent
  ) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(sender, "&cYou must select at least one player");
      return;
    }

    for (Player player : players) {
      User user = DedsafioPlugin.getInstance().userManager.getUser(player);
      user.setDead(false);

      if (!silent) {
        user.setAlertRevive(true);
        user.addRevivedTimes();
      }
    }

    if (players.size() == 1) {
      ChatUtil.sendMessage(sender, "&aYou revived " + players.iterator().next().getName() + " successfully");
    } else {
      ChatUtil.sendMessage(sender, "&aYou revived " + players.size() + " players successfully");
    }
  }
}
