package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Attackable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class InfernalSword extends CustomItem implements Attackable {
  public InfernalSword() {
    super(
      new ItemSettings(Material.DIAMOND_SWORD, "infernal_sword")
        .setDisplayName("&c&lHealing Infernal Sword").setItemId(1)
    );
  }

  @Override
  public void onAttack(EntityDamageByEntityEvent event, Player player, Entity target) {
    var currentHealth = player.getHealth();
    var newHealth = currentHealth + 1;

    if (newHealth <= 20) {
      player.setHealth(currentHealth + 1);
    }
  }
}
