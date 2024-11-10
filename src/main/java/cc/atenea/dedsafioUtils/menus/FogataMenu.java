package cc.atenea.dedsafioUtils.menus;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.MagicGUI;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class FogataMenu {
  private final DedsafioPlugin plugin = DedsafioPlugin.getInstance();
  private final int HEAD_SLOT = 22;
  private final List<OfflinePlayer> players = plugin.userManager
    .getDeadUsers().stream()
    .map(p -> Bukkit.getOfflinePlayer(p.getUuid())).toList();
  private int currentHeadIndex = 0;

  public void open(Player user) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(user, "&cNo hay jugadores muertos para revivir.");
      return;
    }

    MagicGUI fogataGui = MagicGUI.create("&f七七七七七七七\uEAA8", 54);
    fogataGui.setAutoRemove(true);

    fogataGui.setItem(HEAD_SLOT, getItemWithSkull());
    fogataGui.setItem(40, getReviveItem(), this::processClick);
    fogataGui.setItem(19, getBackItem(), (player, gui, slot, type) -> {
      if (currentHeadIndex < players.size() - 1) {
        currentHeadIndex = players.size() - 1;
      }

      fogataGui.setItem(HEAD_SLOT, getItemWithSkull());
    });

    fogataGui.setItem(25, getNextItem(), (player, gui, slot, type) -> {
      currentHeadIndex++;

      if (currentHeadIndex > players.size() - 1) {
        currentHeadIndex = 0;
      }

      fogataGui.setItem(HEAD_SLOT, getItemWithSkull());
    });

    fogataGui.open(user);
  }

  private ItemStack getItemWithSkull() {
    OfflinePlayer thePlayer = Bukkit.getOfflinePlayer(players.get(currentHeadIndex).getUniqueId());
    ItemStack giveMeHead = new ItemStack(Material.PLAYER_HEAD);

    SkullMeta meta = (SkullMeta) giveMeHead.getItemMeta();
    assert meta != null;

    meta.setOwningPlayer(thePlayer);
    giveMeHead.setItemMeta(meta);

    return giveMeHead;
  }

  private void processClick(Player player, MagicGUI gui, int slot, ClickType type) {
    OfflinePlayer offlinePlayer = players.get(currentHeadIndex);
    if (offlinePlayer == null || offlinePlayer.getUniqueId().equals(player.getUniqueId())) return;

    User selfProfile = plugin.userManager.getUser(player);
    if (!selfProfile.hasSoul()) {
      ChatUtil.sendMessage(player, "&cNo tienes alma");
      return;
    }

    User offlineProfile = plugin.userManager.getUser(offlinePlayer.getUniqueId());
    if (!offlineProfile.isDead()) {
      ChatUtil.sendMessage(player, "&cEl jugador no esta muerto");
      return;
    }

    if (offlinePlayer.getName() == null) {
      ChatUtil.sendMessage(player, "&cNo hemos podido encontrar al jugador");
      return;
    }

    CustomItem customItem = ItemManager.getItem("resurrection_spoon");
    ItemStack itemStack = ItemManager.getItemInList(player.getInventory().getContents(), customItem);
    EquipmentSlot handUsed = player.getInventory().getHeldItemSlot() == 0 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;

    if (itemStack == null) {
      ChatUtil.sendMessage(player, "&cDebes tener una cuchara de resurrección en tu inventario");
      return;
    }

    selfProfile.setSoul(false);
    offlineProfile.setDead(false);
    offlineProfile.setAlertRevive(true);
    offlineProfile.addRevivedTimes();

    if (itemStack.getAmount() > 1) {
      itemStack.setAmount(itemStack.getAmount() - 1);
    } else {
      player.getInventory().setItem(handUsed, null);
    }

    Bukkit.getBanList(BanList.Type.PROFILE).pardon(offlinePlayer.getName());
    ChatUtil.sendMessage(player, "&aHas revivido a " + offlinePlayer.getName() + "!");

    gui.close(player);
  }

  private ItemStack getBackItem() {
    return getEmptyItem("&7« Back", List.of(ChatUtil.translate("&7Click para retroceder")));
  }

  private ItemStack getNextItem() {
    return getEmptyItem("&7» Next", List.of(ChatUtil.translate("&7Click para avanzar")));
  }

  private ItemStack getReviveItem() {
    return getEmptyItem("&7Revivir", List.of(ChatUtil.translate("&7Click para revivir")));
  }

  private ItemStack getEmptyItem(String name, List<String> lore) {
    ItemStack nextItem = new ItemStack(Material.ARROW);
    ItemMeta nextMeta = nextItem.getItemMeta();

    if (nextMeta != null) {
      nextMeta.setCustomModelData(1001);
      nextMeta.setDisplayName(ChatUtil.translate(name));
      nextMeta.setLore(ChatUtil.translate(lore));
    }

    nextItem.setItemMeta(nextMeta);
    return nextItem;
  }
}
