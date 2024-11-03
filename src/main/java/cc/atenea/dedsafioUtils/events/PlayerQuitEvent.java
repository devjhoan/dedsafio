package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {
  @EventHandler
  public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.setQuitMessage(null);

    if (ConfigResource.LeaveMessageEnabled) {
      ChatUtil.broadcast(ConfigResource.LeaveMessage.replace("{player}", player.getName()));
    }
  }
}
