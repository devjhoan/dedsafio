package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class EnderBagItem extends CustomItem implements Clickable {
  public EnderBagItem() {
    super(
      new ItemSettings(Material.CLOCK, "ender_bag")
        .setDisplayName("&1Ender Bag").setItemId(1)
        .setLore(List.of(
          "&dTu propio Ender Chest,",
          "&d&l¡Ahora en tu bolsillo!"
        ))
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    player.openInventory(player.getEnderChest());
  }
}
