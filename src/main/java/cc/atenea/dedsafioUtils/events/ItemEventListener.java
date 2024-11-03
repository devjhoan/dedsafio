package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.items.core.Attackable;
import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemEventListener implements Listener {
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    Action action = event.getAction();

    if (ItemManager.isCustomItem(item)) {
      var meta = item.getItemMeta();
      if (meta == null) return;

      CustomItem customItem = ItemManager.getItem(item.getType(), meta.getCustomModelData());
      if (customItem == null) return;
      if (!(customItem instanceof Clickable clickableItem)) return;

      if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
        clickableItem.onRightClick(event);
      } else if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
        clickableItem.onLeftClick(event);
      }
    }
  }

  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    Entity entityDamager = event.getDamager();
    Entity target = event.getEntity();

    if (!(entityDamager instanceof Player player)) return;
    ItemStack item = player.getInventory().getItemInMainHand();

    if (ItemManager.isCustomItem(item)) {
      var meta = item.getItemMeta();
      if (meta == null) return;

      CustomItem customItem = ItemManager.getItem(item.getType(), meta.getCustomModelData());
      if (customItem == null) return;
      if (!(customItem instanceof Attackable attackableItem)) return;

      attackableItem.onAttack(event, player, target);
    }
  }
}
