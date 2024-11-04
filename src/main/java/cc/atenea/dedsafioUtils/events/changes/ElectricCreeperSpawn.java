package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ElectricCreeperSpawn implements Listener {
  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent event) {
    if (!ConfigResource.AlwaysSpawnElectricCreepers) return;

    if (event.getEntityType() == EntityType.CREEPER) {
      Creeper creeper = (Creeper) event.getEntity();
      creeper.setPowered(true);
    }
  }
}
