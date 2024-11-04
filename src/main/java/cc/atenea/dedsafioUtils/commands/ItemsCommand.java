package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import cc.atenea.dedsafioUtils.menus.CustomItemsMenu;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AEntitySelectorArgument;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;

@Command("items")
@Permission("items")
public class ItemsCommand {

  @Default()
  public static void run(CommandSender sender) {
    ChatUtil.sendMessage(sender, new String[]{
      "&7/items list",
      "&7/items give <player> <item>",
      "&7/items gui"
    });
  }

  @Subcommand("list")
  public static void list(CommandSender sender) {
    var items = ItemManager.getItems();

    for (Map.Entry<String, CustomItem> item : items.entrySet()) {
      String[] message = new String[]{
        ChatUtil.NORMAL_LINE,
        item.getValue().getDisplayName(),
        "▶ &7Model ID: &f" + item.getValue().getCustomModelData(),
        "▶ &7Item ID: &f" + item.getKey(),
        "▶ &7Item: &f" + item.getValue().getItemStack().getType().name()
      };

      ChatUtil.sendMessage(sender, message);
    }

    ChatUtil.sendMessage(sender, ChatUtil.NORMAL_LINE);
  }

  @Subcommand("gui")
  public static void gui(CommandSender sender) {
    CustomItemsMenu menu = new CustomItemsMenu();
    menu.open((Player) sender);
  }

  @Subcommand("give")
  public static void run(
    CommandSender sender,
    @AEntitySelectorArgument.ManyPlayers Collection<Player> players,
    @AStringArgument String itemId
  ) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(sender, "&cNo players found");
      return;
    }

    CustomItem item = ItemManager.getItem(itemId);
    if (item == null) {
      ChatUtil.sendMessage(sender, "&cItem not found");
      return;
    }

    for (Player player : players) {
      if (player.getInventory().firstEmpty() == -1) {
        ChatUtil.sendMessage(
          sender,
          "&c" + player.getName() + " does not have enough space in their inventory"
        );
        continue;
      }

      player.getInventory().addItem(item.getItemStack());
    }

    ChatUtil.sendMessage(sender, "&aItem(s) added to " + players.size() + " player(s)");
  }

  @Subcommand("give")
  public static void run(
    CommandSender sender,
    @AEntitySelectorArgument.ManyPlayers Collection<Player> players
  ) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(sender, "&cNo players found");
      return;
    }

    ItemManager.getItems().forEach((itemId, item) -> {
      for (Player player : players) {
        if (player.getInventory().firstEmpty() == -1) {
          ChatUtil.sendMessage(
            sender,
            "&c" + player.getName() + " does not have enough space in their inventory"
          );
          continue;
        }

        player.getInventory().addItem(item.getItemStack());
      }
    });

    ChatUtil.sendMessage(sender, "&aItem(s) added to " + players.size() + " player(s)");
  }
}
