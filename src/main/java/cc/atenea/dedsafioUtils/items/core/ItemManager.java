package cc.atenea.dedsafioUtils.items.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemManager {
  private static final Map<String, CustomItem> items = new HashMap<>();

  public static void registerItem(CustomItem customItem) {
    for (Map.Entry<String, CustomItem> entry : items.entrySet()) {
      if (entry.getValue().getMaterial() == customItem.getMaterial() && entry.getValue().getCustomModelData() == customItem.getCustomModelData()) {
        throw new IllegalArgumentException("Item with material " + customItem.getMaterial() + " and custom model data " + customItem.getCustomModelData() + " is already registered.");
      }
    }

    items.put(customItem.getItemId(), customItem);
  }

  public static CustomItem getItem(String itemId) {
    return items.get(itemId);
  }

  public static CustomItem getItem(Material material, int customModelData) {
    for (Map.Entry<String, CustomItem> entry : items.entrySet()) {
      if (entry.getValue().getMaterial() == material && entry.getValue().getCustomModelData() == customModelData) {
        return entry.getValue();
      }
    }

    return null;
  }

  public static boolean hasItem(String itemId) {
    return items.containsKey(itemId);
  }

  public static boolean hasItem(int customModelData) {
    return items.values().stream().anyMatch(customItem -> customItem.getCustomModelData() == customModelData);
  }

  public static Map<String, CustomItem> getItems() {
    return items;
  }

  public static boolean isCustomItem(ItemStack item) {
    if (item == null || !item.hasItemMeta() || !Objects.requireNonNull(item.getItemMeta()).hasCustomModelData()) {
      return false;
    }

    int customModelData = item.getItemMeta().getCustomModelData();
    return hasItem(customModelData);
  }
}
