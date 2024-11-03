package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import org.bukkit.Material;

public class SunblockItem extends CustomItem implements Clickable {
  public SunblockItem() {
    super(
      new ItemSettings(Material.PAPER, "sunblock")
        .setDisplayName("&6&lBloqueador Solar").setItemId(1)
    );
  }
}
