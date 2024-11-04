package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoorsCausesInstantKill implements Listener {
  @EventHandler
  public void onDoorUse(PlayerInteractEvent event) {
    if (!ConfigResource.ButtonInstantKill) return;
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

    Player player = event.getPlayer();
    if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;
    if (player.getGameMode() != GameMode.SURVIVAL) return;

    if (event.getClickedBlock() == null ||
      (event.getClickedBlock().getType() != Material.DARK_OAK_DOOR &&
        event.getClickedBlock().getType() != Material.ACACIA_DOOR &&
        event.getClickedBlock().getType() != Material.BAMBOO_DOOR &&
        event.getClickedBlock().getType() != Material.CRIMSON_DOOR &&
        event.getClickedBlock().getType() != Material.CHERRY_DOOR &&
        event.getClickedBlock().getType() != Material.COPPER_DOOR &&
        event.getClickedBlock().getType() != Material.BIRCH_DOOR &&
        event.getClickedBlock().getType() != Material.JUNGLE_DOOR &&
        event.getClickedBlock().getType() != Material.WARPED_DOOR &&
        event.getClickedBlock().getType() != Material.OAK_DOOR &&
        event.getClickedBlock().getType() != Material.SPRUCE_DOOR &&
        event.getClickedBlock().getType() != Material.IRON_DOOR &&
        event.getClickedBlock().getType() != Material.MANGROVE_DOOR)
    ) return;

    player.damage(50000);
  }
}
