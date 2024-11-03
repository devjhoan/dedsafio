package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ResurrectionSpoonItem extends CustomItem implements Clickable {
  public ResurrectionSpoonItem() {
    super(
      new ItemSettings(Material.BOOK, "resurrection_spoon")
        .setDisplayName("&6&lCuchara de resurrección").setItemId(2)
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ChatUtil.sendMessage(player, "&cEste item aún no se encuentra disponible");
  }
}
