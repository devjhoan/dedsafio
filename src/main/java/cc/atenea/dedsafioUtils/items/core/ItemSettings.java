package cc.atenea.dedsafioUtils.items.core;

import org.bukkit.Material;

import java.util.List;

public class ItemSettings {
  public final Material material;
  public final String itemId;
  public int customModelData;
  public String displayName;
  public boolean privateItem;
  public List<String> lore;

  public ItemSettings(Material material, String name) {
    this.material = material;
    this.customModelData = 0;
    this.displayName = "";
    this.itemId = name.toLowerCase().replaceAll(" ", "_");
    this.lore = List.of();
    this.privateItem = false;
  }

  public ItemSettings setItemId(int customModelData) {
    this.customModelData = customModelData;
    return this;
  }

  public ItemSettings setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public ItemSettings setLore(List<String> lore) {
    this.lore = lore;
    return this;
  }

  public ItemSettings setPrivateItem(boolean privateItem) {
    this.privateItem = privateItem;
    return this;
  }
}
