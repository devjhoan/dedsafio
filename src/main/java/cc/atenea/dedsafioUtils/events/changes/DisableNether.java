package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Objects;

public class DisableNether implements Listener {
  @EventHandler
  public void onPlayerPortal(PlayerPortalEvent event) {
    Player player = event.getPlayer();

    if (!ConfigResource.NetherDisabled) return;
    if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;

    World world = Objects.requireNonNull(event.getTo()).getWorld();
    if (world == null) return;

    if (event.getTo() != null && event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
      event.setCancelled(true);
      player.sendMessage("El Nether está desactivado en este servidor.");
    }
  }
}
