package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
  @EventHandler
  public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(null);

    if (player.getGameMode() == GameMode.SPECTATOR) {
      player.setGameMode(GameMode.SURVIVAL);
    }

    if (ConfigResource.WelcomeMessageEnabled) {
      ChatUtil.broadcast(
        ConfigResource.WelcomeMessage
          .replace("{player}", player.getName())
          .replace("{r-color}", ConfigResource.AnnounceColor)
      );
    }

    DedsafioPlugin.getInstance().userManager.getUser(player.getUniqueId());
  }
}
