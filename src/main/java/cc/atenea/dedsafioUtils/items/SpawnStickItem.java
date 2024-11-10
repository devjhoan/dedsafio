package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.items.core.Attackable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class SpawnStickItem extends CustomItem implements Attackable {
  public SpawnStickItem() {
    super(
      new ItemSettings(Material.STICK, "spawn_stick")
        .setItemId(238).setDisplayName("&6&lSpawn Stick")
        .setLore(List.of("&7Golpea a alguien para enviarlo al spawn"))
    );
  }

  @Override
  public void onAttack(EntityDamageByEntityEvent event, Player player, Entity target) {
    event.setCancelled(true);

    if (target instanceof Player targetPlayer) {
      Location location = targetPlayer.getRespawnLocation();

      ChatUtil.sendTitle(targetPlayer, ConfigResource.WhiteScreen, "", 10, 40, 20);
      ChatUtil.sendMessage(player, "&aEl jugador " + targetPlayer.getName() + " ha sido teletransportado a su spawn");

      Bukkit.getScheduler().runTaskLater(DedsafioPlugin.getInstance(), () -> targetPlayer.teleport(location == null ? player.getWorld().getSpawnLocation() : location), 10);
    }
  }
}
