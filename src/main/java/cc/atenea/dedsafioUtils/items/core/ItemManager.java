package cc.atenea.dedsafioUtils.items.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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

  public static boolean hasItem(int customModelData) {
    return items.values().stream().anyMatch(customItem -> customItem.getCustomModelData() == customModelData);
  }

  public static Map<String, CustomItem> getItems() {
    return items.entrySet().stream()
      .filter(entry -> !entry.getValue().isPrivateItem())
      .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
  }

  public static boolean isCustomItem(ItemStack item) {
    if (item == null || !item.hasItemMeta() || !Objects.requireNonNull(item.getItemMeta()).hasCustomModelData()) {
      return false;
    }

    int customModelData = item.getItemMeta().getCustomModelData();
    return hasItem(customModelData);
  }

  public static boolean isCustomItem(Material material, int customModelData) {
    return getItem(material, customModelData) != null;
  }

  public static ItemStack getItemInList(List<ItemStack> items, CustomItem customItem) {
    return items.stream()
      .filter(item -> {
        if (item == null || item.getType() == Material.AIR || item.getType() != customItem.getMaterial()) return false;

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta != null && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == customItem.getCustomModelData();
      })
      .findFirst()
      .orElse(null);
  }

  public static ItemStack getItemInList(ItemStack[] items, CustomItem customItem) {
    return getItemInList(Arrays.asList(items), customItem);
  }
}
