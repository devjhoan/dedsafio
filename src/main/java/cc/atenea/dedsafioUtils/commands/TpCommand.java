package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.AEntitySelectorArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.ALocationArgument;
import dev.jorel.commandapi.arguments.LocationType;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@Command("dtp")
@Permission("dtp")
public class TpCommand {
  @Default()
  public static void tp(CommandSender sender) {
    ChatUtil.sendMessage(sender, LanguageResource.TP_COMMAND_MESSAGE_HELP);
  }

  @Default()
  public static void tp(
    CommandSender sender,
    @AEntitySelectorArgument.ManyPlayers Collection<Player> players,
    @ALocationArgument(LocationType.BLOCK_POSITION) Location location,
    @AIntegerArgument(min = 0) int fadeIn,
    @AIntegerArgument(min = 0) int stay,
    @AIntegerArgument(min = 0) int fadeOut
  ) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(sender, "&cNo players found");
      return;
    }

    DedsafioPlugin plugin = DedsafioPlugin.getInstance();

    for (Player player : players) {
      player.sendTitle(ChatUtil.translate("\uE97D"), "", fadeIn, stay, fadeOut);
      plugin.getServer().getScheduler().runTaskLater(plugin, () -> player.teleport(location), stay + fadeOut);
    }

    ChatUtil.sendMessage(sender, LanguageResource.TP_COMMAND_MESSAGE_SUCCESS);
  }
}
