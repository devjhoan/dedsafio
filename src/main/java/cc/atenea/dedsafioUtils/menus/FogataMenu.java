package cc.atenea.dedsafioUtils.menus;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.MagicGUI;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;

public class FogataMenu {
  private final DedsafioPlugin plugin = DedsafioPlugin.getInstance();
  private final int HEAD_SLOT = 22;
  private int currentHeadIndex = 0;
  private Player user;

  private final List<OfflinePlayer> players = Bukkit.getBannedPlayers().stream()
    .filter(p -> {
      BanList<?> banList = Bukkit.getBanList(BanList.Type.NAME);
      return Objects.equals(
        Objects.requireNonNull(banList.getBanEntry(Objects.requireNonNull(p.getName()))).getReason(), ConfigResource.DeathSystemBanMessage);
    }).toList();

  public void open(Player user) {
    if (players.isEmpty()) {
      ChatUtil.sendMessage(user, "No hay jugadores muertos para revivir.");
      return;
    }

    MagicGUI fogataGui = MagicGUI.create("&f七七七七七七七\uEAA8", 54);
    fogataGui.setAutoRemove(true);
    this.user = user;

    fogataGui.setItem(HEAD_SLOT, getItemWithSkull(players));
    fogataGui.setItem(40, getReviveItem(), this::processClick);
    fogataGui.setItem(19, getBackItem(), (player, gui, slot, type) -> {
      currentHeadIndex--;

      if (currentHeadIndex < players.size() - 1) {
        currentHeadIndex = players.size() - 1;
      }

      fogataGui.setItem(HEAD_SLOT, getItemWithSkull(players));
    });

    fogataGui.setItem(25, getNextItem(), (player, gui, slot, type) -> {
      currentHeadIndex++;

      if (currentHeadIndex > players.size() - 1) {
        currentHeadIndex = 0;
      }

      fogataGui.setItem(HEAD_SLOT, getItemWithSkull(players));
    });

    fogataGui.open(user);
  }

  private ItemStack getItemWithSkull(List<OfflinePlayer> players) {
    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

    if (skullMeta != null) {
      skullMeta.setOwningPlayer(players.get(currentHeadIndex));
      skull.setItemMeta(skullMeta);
    }

    return skull;
  }

  private void processClick(Player player, MagicGUI gui, int slot, ClickType type) {
    OfflinePlayer head = players.get(currentHeadIndex);
    if (head == null) return;

    User selfPlayer = plugin.userManager.getUser(player);
    if (!selfPlayer.hasSoul()) {
      ChatUtil.sendMessage(player, "No tienes alma");
      return;
    }

    ChatUtil.sendMessage(user, "Has revivido a " + players.get(currentHeadIndex).getName());
    plugin.userManager.getUser(player).setSoul(false);

    Bukkit.getBanList(BanList.Type.PROFILE)
      .pardon(Objects.requireNonNull(head.getName()));

    gui.close();
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
