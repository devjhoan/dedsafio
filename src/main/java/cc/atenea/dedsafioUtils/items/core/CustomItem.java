package cc.atenea.dedsafioUtils.items.core;

import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CustomItem {
  private final Material material;
  private final int customModelData;
  private final String itemId;
  private final String displayName;
  private final List<String> lore;
  private final boolean privateItem;

  public CustomItem(ItemSettings settings) {
    this.material = settings.material;
    this.customModelData = settings.customModelData;
    this.displayName = ChatUtil.translate(settings.displayName);
    this.itemId = settings.itemId;
    this.lore = ChatUtil.translate(settings.lore);
    this.privateItem = settings.privateItem;
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      meta.setCustomModelData(customModelData);
      meta.setDisplayName(displayName);

      if (lore != null && !lore.isEmpty())
        meta.setLore(lore);

      item.setItemMeta(meta);
    }

    return item;
  }

  public int getCustomModelData() {
    return customModelData;
  }

  public String getItemId() {
    return itemId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Material getMaterial() {
    return material;
  }

  public boolean isPrivateItem() {
    return privateItem;
  }

  public ItemStack getItemStack() {
    return createItem();
  }
}
