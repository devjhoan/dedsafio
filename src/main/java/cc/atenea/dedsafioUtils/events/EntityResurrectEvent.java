package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityResurrectEvent implements Listener {

  @EventHandler
  public void onEntityResurrect(org.bukkit.event.entity.EntityResurrectEvent event) {
    if (event.getHand() == null) return;
    if (event.getEntity() instanceof Player player && ConfigResource.TotemUseMessageEnabled) {
      ChatUtil.broadcast(ConfigResource.TotemUseMessage.replace("{player}", player.getName()));
    }
  }
}
