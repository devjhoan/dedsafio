package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PiglinsDropGoldNuggets implements Listener {
  @EventHandler
  public void onPiglinDeath(EntityDeathEvent event) {
    if (!ConfigResource.PiglinsDropGoldNuggets) return;

    if (event.getEntityType() == EntityType.PIGLIN) {
      if (event.getEntity().getKiller() != null) {
        Player player = event.getEntity().getKiller();
        if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;
      }

      event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, getLootChance()));
    }
  }

  public int getLootChance() {
    int min = 3;
    int max = 5;

    return min + (int) (Math.random() * ((max - min) + 1));
  }
}
