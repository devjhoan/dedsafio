package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
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

    ItemStack item = player.getInventory().getItemInMainHand();
    player.getInventory().remove(item);
    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

    for (ItemStack itemStack : player.getInventory().getArmorContents()) {
      if (itemStack != null) {
        itemStack.setDurability((short) 0);
      }
    }
  }
}
