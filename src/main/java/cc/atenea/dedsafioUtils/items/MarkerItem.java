package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MarkerItem extends CustomItem implements Clickable {
  private final NamespacedKey pos1Key;
  private final NamespacedKey pos2Key;

  public MarkerItem(Plugin plugin) {
    super(
      new ItemSettings(Material.GOLDEN_HOE, "marker_item")
        .setDisplayName("&6&lHerramienta de marcación").setItemId(1)
        .setPrivateItem(true)
        .setLore(List.of(
          "&7Click izquierdo para definir la primera posición",
          "&7Click derecho para definir la segunda posición"
        ))
    );

    pos1Key = new NamespacedKey(plugin, "pos1");
    pos2Key = new NamespacedKey(plugin, "pos2");
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    event.setCancelled(true);

    if (event.getClickedBlock() != null) {
      Location pos2 = event.getClickedBlock().getLocation();
      saveLocationToItem(event, pos2, pos2Key);
      ChatUtil.sendMessage(event.getPlayer(), "&aSegunda posición definida en: &7" + pos2.getBlockX() + ", " + pos2.getBlockY() + ", " + pos2.getBlockZ());
    }
  }

  @Override
  public void onLeftClick(PlayerInteractEvent event) {
    event.setCancelled(true);

    if (event.getClickedBlock() != null) {
      Location pos1 = event.getClickedBlock().getLocation();
      saveLocationToItem(event, pos1, pos1Key);
      ChatUtil.sendMessage(event.getPlayer(), "&aSegunda posición definida en: &7" + pos1.getBlockX() + ", " + pos1.getBlockY() + ", " + pos1.getBlockZ());
    }
  }

  @Override
  public void onDropItem(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  private void saveLocationToItem(PlayerInteractEvent event, Location loc, NamespacedKey key) {
    ItemStack itemStack = event.getItem();
    if (itemStack == null) return;

    var itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return;

    PersistentDataContainer data = itemMeta.getPersistentDataContainer();
    String locString = loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();

    data.set(key, PersistentDataType.STRING, locString);
    event.getItem().setItemMeta(itemMeta);
  }
}
