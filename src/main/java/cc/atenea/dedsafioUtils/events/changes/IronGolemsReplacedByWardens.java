package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class IronGolemsReplacedByWardens implements Listener {

  @EventHandler
  public void onIronGolemSpawn(CreatureSpawnEvent event) {
    if (!ConfigResource.IronGolemsReplacedByWardens) return;
    if (event.getEntityType() == EntityType.IRON_GOLEM) {
      Location location = event.getLocation();

      event.setCancelled(true);
      World world = location.getWorld();
      if (world == null) return;

      location.getWorld().spawnEntity(location, EntityType.WARDEN);
    }
  }
}
