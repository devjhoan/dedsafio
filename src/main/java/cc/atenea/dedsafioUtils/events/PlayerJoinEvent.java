package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
  @EventHandler
  public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    Player player = event.getPlayer();
    User userProfile = DedsafioPlugin.getInstance().userManager.getUser(player);
    event.setJoinMessage(null);

    if (userProfile.isDead()) {
      player.kickPlayer(ChatUtil.translate("&aEstás muerto, alguien debe revivirte."));
      return;
    }

    if (userProfile.getAlertRevive()) {
      ChatUtil.broadcast("&3✧ &b&l" + player.getName() + " &3&lha resucitado! ✧");
      userProfile.setAlertRevive(false);
    }

    if (player.getGameMode() == GameMode.SPECTATOR) {
      Location spawnLocation = player.getWorld().getSpawnLocation();

      player.teleport(spawnLocation);
      player.setGameMode(GameMode.SURVIVAL);
    }

    if (ConfigResource.WelcomeMessageEnabled) {
      ChatUtil.broadcast(
        ConfigResource.WelcomeMessage
          .replace("{player}", player.getName())
          .replace("{r-color}", ConfigResource.AnnounceColor)
      );
    }

  }
}
