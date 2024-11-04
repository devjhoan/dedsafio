package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SpiderWebsOnHit implements Listener {
  @EventHandler
  public void onSpiderAttack(EntityDamageByEntityEvent event) {
    if (!ConfigResource.SpiderWebsOnHit) return;

    if (event.getDamager().getType() == EntityType.SPIDER && event.getEntity() instanceof Player player) {
      if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;

      Location location = player.getLocation();
      Block blockUnderPlayer = location.getBlock();

      if (blockUnderPlayer.getType() == Material.AIR) {
        blockUnderPlayer.setType(Material.COBWEB);
      }
    }
  }
}
