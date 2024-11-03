package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.animations.Animations;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerDeathEvent implements Listener {
  private final DedsafioPlugin plugin;
  private final Map<Player, Location> deathLocations = new HashMap<>();

  public PlayerDeathEvent(DedsafioPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  private void onDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
    if (!ConfigResource.DeathSystemEnabled) return;

    Player player = event.getEntity();
    if (player.hasPermission(ConfigResource.DeathSystemBypassPermission)) return;

    String deathMessage = ChatUtil.translate(
      ConfigResource.DeathSystemMessage
        .replace("{player}", player.getName())
        .replace("{deathMessage}", event.getDeathMessage() == null ? "" : event.getDeathMessage())
    );

    event.setDeathMessage(null);
    if (ConfigResource.DeathSystemAnimationEnabled) {
      plugin.animations.playAnimation(Animations.Muerte, ConfigResource.DeathSystemAnimationPosition, ConfigResource.DeathSystemAnimationSound);
    }

    deathLocations.put(player, player.getLocation());
    player.setGameMode(GameMode.SPECTATOR);

    if (Objects.equals(ConfigResource.DeathSystemAnimationShowTo, "all")) {
      for (Player p : plugin.getServer().getOnlinePlayers()) {
        sendTimedActionBar(p, deathMessage, 15);
      }
    } else if (Objects.equals(ConfigResource.DeathSystemAnimationShowTo, "player")) {
      sendTimedActionBar(player, deathMessage, 15);
    }

    Bukkit.getScheduler().runTaskLater(plugin, () -> {
      if (ConfigResource.DeathSystemBanEnabled) {
        player.ban(ConfigResource.DeathSystemBanMessage, (Date) null, ConfigResource.DeathSystemBanMessage);
      } else {
        player.kickPlayer(deathMessage);
      }
    }, ConfigResource.DeathSystemBanAfterSeconds * 20L);
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();
    Location deathLocation = deathLocations.get(player);

    if (deathLocation != null) {
      event.setRespawnLocation(deathLocation);
      deathLocations.remove(player);
    }
  }

  public void sendTimedActionBar(Player player, String message, int durationInSeconds) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    Bukkit.getScheduler().runTaskLater(plugin, () -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("")), 20L * durationInSeconds);
  }
}
