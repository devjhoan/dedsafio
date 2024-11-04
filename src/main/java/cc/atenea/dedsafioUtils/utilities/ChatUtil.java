package cc.atenea.dedsafioUtils.utilities;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class ChatUtil {
  public static final String NORMAL_LINE = "&7&m-----------------------------";
  private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

  public static String translate(String text) {
    Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
    Matcher matcher = hexPattern.matcher(text);
    StringBuilder buffer = new StringBuilder(text.length() + 4 * 8);

    while (matcher.find()) {
      String group = matcher.group(1);
      matcher.appendReplacement(buffer, COLOR_CHAR + "x"
        + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
        + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
        + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
      );
    }

    return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
  }

  public static String[] translate(String[] array) {
    for (int i = 0; i < array.length; i++) {
      array[i] = translate(array[i]);
    }

    return array;
  }

  public static List<String> translate(List<String> list) {
    return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
  }

  public static void sendMessage(CommandSender sender, String text) {
    sender.sendMessage(translate(text));
  }

  public static void sendMessage(CommandSender sender, String[] array) {
    sender.sendMessage(ChatUtil.translate(array));
  }

  public static void sendActionBar(Player player, String text) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(translate(text)));
  }

  public static void broadcast(String text) {
    Bukkit.broadcastMessage(translate(text));
  }

  public static void broadcast(String[] text) {
    Bukkit.broadcastMessage(
      Arrays.stream(text).map(ChatUtil::translate).collect(Collectors.joining("\n"))
    );
  }
}