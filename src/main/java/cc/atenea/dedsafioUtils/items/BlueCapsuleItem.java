package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import org.bukkit.Material;

public class BlueCapsuleItem extends CustomItem {
  public BlueCapsuleItem() {
    super(
      new ItemSettings(Material.YELLOW_DYE, "blue_capsule")
        .setDisplayName("&9&lPíldora").setItemId(1)
    );
  }
}
