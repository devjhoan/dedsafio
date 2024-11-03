package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Attackable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import org.bukkit.Material;

public class GhostSword extends CustomItem implements Attackable {
  public GhostSword() {
    super(
      new ItemSettings(Material.DIAMOND_SWORD, "ghost_sword")
        .setDisplayName("&b&lEspada Fantasmal").setItemId(2)
    );
  }
}
