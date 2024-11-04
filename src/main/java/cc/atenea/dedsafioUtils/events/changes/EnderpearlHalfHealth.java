package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EnderpearlHalfHealth implements Listener {

  @EventHandler
  public void onEnderpearlDamage(ProjectileLaunchEvent event) {
    if (!ConfigResource.EnderpearlHalfHealth) return;
    if (event.getEntityType() != EntityType.ENDER_PEARL) return;

    ProjectileSource source = event.getEntity().getShooter();
    if (source instanceof Player player) {
      if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;
      if (player.getGameMode() != GameMode.SURVIVAL) return;

      player.setHealth(player.getHealth() / 2);
    }
  }
}
