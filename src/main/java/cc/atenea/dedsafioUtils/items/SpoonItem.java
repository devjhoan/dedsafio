package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpoonItem extends CustomItem implements Clickable {
  public SpoonItem() {
    super(
      new ItemSettings(Material.BOOK, "spoon")
        .setDisplayName("&6&lCuchara").setItemId(3)
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ChatUtil.sendMessage(player, "&cEste item aún no se encuentra disponible");
  }
}
