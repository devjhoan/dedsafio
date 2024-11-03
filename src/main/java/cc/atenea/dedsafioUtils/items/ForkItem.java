package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ForkItem extends CustomItem implements Clickable {
  public ForkItem() {
    super(
      new ItemSettings(Material.BLACK_DYE, "fork")
        .setDisplayName("&5&lTenedor").setItemId(1)
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ChatUtil.sendMessage(player, "&cEste item aun no se encuentra disponible");
  }
}
