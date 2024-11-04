package cc.atenea.dedsafioUtils.events.changes;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ButtonInstantKill implements Listener {
  @EventHandler
  public void onButtonPress(PlayerInteractEvent event) {
    if (!ConfigResource.ButtonInstantKill) return;
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

    Player player = event.getPlayer();
    if (player.hasPermission(ConfigResource.ChangesBypassPermission)) return;
    if (player.getGameMode() != GameMode.SURVIVAL) return;

    if (event.getClickedBlock() == null ||
      (event.getClickedBlock().getType() != Material.STONE_BUTTON &&
        event.getClickedBlock().getType() != Material.OAK_BUTTON &&
        event.getClickedBlock().getType() != Material.SPRUCE_BUTTON &&
        event.getClickedBlock().getType() != Material.BIRCH_BUTTON &&
        event.getClickedBlock().getType() != Material.JUNGLE_BUTTON &&
        event.getClickedBlock().getType() != Material.ACACIA_BUTTON &&
        event.getClickedBlock().getType() != Material.DARK_OAK_BUTTON &&
        event.getClickedBlock().getType() != Material.CRIMSON_BUTTON &&
        event.getClickedBlock().getType() != Material.WARPED_BUTTON)
    ) return;

    player.damage(50000);
  }
}
