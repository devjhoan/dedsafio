package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PortableGoldenAnvilItem extends CustomItem implements Clickable {
  public PortableGoldenAnvilItem() {
    super(
      new ItemSettings(Material.BOOK, "portable_golden_anvil")
        .setDisplayName("&6&lYunque Portátil Dorado").setItemId(1)
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    EquipmentSlot handUsed = event.getHand();
    ItemStack item = handUsed == EquipmentSlot.OFF_HAND
      ? player.getInventory().getItemInOffHand()
      : player.getInventory().getItemInMainHand();

    if (handUsed == null) return;

    boolean isArmorFullyRepaired = true;
    for (ItemStack itemStack : player.getInventory().getArmorContents()) {
      if (itemStack != null && itemStack.getDurability() > 0) {
        isArmorFullyRepaired = false;
        break;
      }
    }

    if (isArmorFullyRepaired) {
      ChatUtil.sendActionBar(player, "&cTu armadura no necesita repararse.");
      return;
    }

    if (item.getAmount() > 1) {
      item.setAmount(item.getAmount() - 1);
    } else {
      player.getInventory().setItem(handUsed, null);
    }

    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
    for (ItemStack itemStack : player.getInventory().getArmorContents()) {
      if (itemStack != null) {
        itemStack.setDurability((short) 0);
      }
    }
  }
}
