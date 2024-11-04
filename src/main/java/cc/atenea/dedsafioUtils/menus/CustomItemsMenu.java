package cc.atenea.dedsafioUtils.menus;

import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.MagicGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomItemsMenu {
  public void open(Player user) {
    MagicGUI fogataGui = MagicGUI.create("&b&lCustom Items", 54);
    fogataGui.setAutoRemove(true);

    List<CustomItem> items = ItemManager.getItems().values().stream().toList();
    if (items.isEmpty()) {
      ChatUtil.sendMessage(user, "&cNo hay items disponibles");
      return;
    }

    ItemManager.getItems()
      .forEach((itemId, item) -> {

      });

    for (int i = 0; i < items.size(); i++) {
      ItemStack item = items.get(i).getItemStack();
      fogataGui.setItem(i, item, (player, gui, slot, clickType) -> player.getInventory().addItem(item));
    }

    fogataGui.open(user);
  }
}
