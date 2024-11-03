package cc.atenea.dedsafioUtils.items.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Attackable {
  default void onAttack(EntityDamageByEntityEvent event, Player player, Entity target) {}
}
