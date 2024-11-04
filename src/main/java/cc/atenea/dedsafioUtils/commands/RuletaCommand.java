package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.animations.Animations;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.AEntitySelectorArgument;
import dev.jorel.commandapi.annotations.arguments.AGreedyStringArgument;
import dev.jorel.commandapi.annotations.arguments.AMultiLiteralArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;

@Command("ruleta")
@Permission("ruleta")
public class RuletaCommand {
  @Default()
  public static void ruleta(CommandSender sender) {
    ChatUtil.sendMessage(sender, LanguageResource.ANIMATION_COMMAND_MESSAGE_HELP);
  }

  @Default()
  public static void ruleta(
    CommandSender sender,
    @AMultiLiteralArgument({"title", "actionbar", "sidebar", "subtitle"}) String type,
    @AMultiLiteralArgument({"red", "orange", "yellow", "green", "blue", "purple", "pink", "cyan"}) String color,
    @AEntitySelectorArgument.ManyPlayers Collection<Player> players,
    @AGreedyStringArgument String message
  ) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(sender, "&cYou must specify at least one player.");
      return;
    }

    Animations animation = getAnimation(color);
    if (animation == null) {
      ChatUtil.sendMessage(sender, LanguageResource.ANIMATION_COMMAND_MESSAGE_INVALID_COLOR);
      return;
    }

    String[] messageToDisplay = Arrays.stream(LanguageResource.MESSAGE_RULETA_COLORS.get(color))
      .map(line -> line
        .replace("{text}", message)
        .replace("{r-color}", ConfigResource.AnnounceColor)
        .replace("{r-off}", ConfigResource.AnnounceOff)
        .replace("{r-green}", ConfigResource.AnnounceGreen)
        .replace("{r-blue}", ConfigResource.AnnounceBlue)
        .replace("{r-red}", ConfigResource.AnnounceRed)
        .replace("{r-purple}", ConfigResource.AnnouncePurple)
        .replace("{r-orange}", ConfigResource.AnnounceOrange)
        .replace("{r-pink}", ConfigResource.AnnouncePink)
        .replace("{r-cyan}", ConfigResource.AnnounceCyan)
        .replace("{r-yellow}", ConfigResource.AnnounceYellow)
      ).toArray(String[]::new);

    for (Player player : players) {
      var plugin = DedsafioPlugin.getInstance();
      plugin.animations.playAnimation(player, animation, type, "dedsafio3:ruleta");

      if (color.equals("red") || color.equals("pink")) {
        Animations nextAnimation = color.equals("red") ? Animations.Reviil : Animations.Nutria;
        String sound = color.equals("red") ? "dedsafio3:reviil" : "dedsafio3:nutria";

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
          plugin.animations.playAnimation(player, nextAnimation, "title", sound);
          sendRuletaMessage(messageToDisplay, player, color.equals("red") ? 15 : 17);
        }, 15 * 20L);

        return;
      }

      sendRuletaMessage(messageToDisplay, player, 15);
    }
  }

  private static void sendRuletaMessage(String[] text, Player player, int delay) {
    var plugin = DedsafioPlugin.getInstance();
    plugin.getServer().getScheduler().runTaskLater(plugin, () -> ChatUtil.sendMessage(player, text), delay * 20L);
  }

  private static Animations getAnimation(String color) {
    return switch (color) {
      case "red" -> Animations.RuletaRed;
      case "orange" -> Animations.RuletaOrange;
      case "yellow" -> Animations.RuletaYellow;
      case "green" -> Animations.RuletaGreen;
      case "blue" -> Animations.RuletaBlue;
      case "purple" -> Animations.RuletaPurple;
      case "pink" -> Animations.RuletaPink;
      case "cyan" -> Animations.RuletaCyan;
      default -> null;
    };
  }
}