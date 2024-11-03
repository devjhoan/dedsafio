package cc.atenea.dedsafioUtils.items.core;

import org.bukkit.event.player.PlayerInteractEvent;

public interface Clickable {
  default void onRightClick(PlayerInteractEvent event) {}
  default void onLeftClick(PlayerInteractEvent event) {}
}
