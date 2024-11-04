package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class VillagersCannotBreed implements Listener {

  @EventHandler
  public void onVillagerBreed(EntityBreedEvent event) {
    if (!ConfigResource.VillagersCannotBreed) return;

    if (event.getEntityType() == EntityType.VILLAGER) {
      event.setCancelled(true);
    }
  }
}
