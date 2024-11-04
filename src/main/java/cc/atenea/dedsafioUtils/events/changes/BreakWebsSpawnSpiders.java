package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakWebsSpawnSpiders implements Listener {

  @EventHandler
  public void onEntityBreak(BlockBreakEvent event) {
    if (!ConfigResource.BreakWebsSpawnPoisonousSpiders) return;

    Player player = event.getPlayer();
    if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;

    if (event.getBlock().getType() == Material.COBWEB) {
      player.getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.CAVE_SPIDER);
    }
  }
}
