package cc.atenea.dedsafioUtils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class MagicGUI {
  private static final Map<UUID, MagicGUI> guis = new HashMap<>();
  private static final Map<UUID, UUID> actives = new HashMap<>();
  private static MagicGUIListener listener;
  private final Map<Integer, ClickAction> actions = new HashMap<>();
  private final Set<Player> viewers = new HashSet<>();
  private final UUID uuid;
  private final Inventory inventory;
  private final String title;
  private final int size;
  private boolean autoRemove;

  private MagicGUI(String title, int size) {
    this.title = title;
    this.size = size;
    this.uuid = UUID.randomUUID();
    this.inventory = Bukkit.createInventory(null, size, title);
    guis.put(this.uuid, this);
  }

  public static MagicGUI create(String title, int size) {
    switch (size) {
      case 54, 45, 36, 27, 18, 9 -> {
      }
      default -> size = 9;
    }
    return new MagicGUI(ChatUtil.translate(title), size);
  }

  public static MagicGUI getFromId(UUID menuId) {
    return guis.get(menuId);
  }

  public static MagicGUI getFromPlayer(Player player) {
    UUID menuId = actives.get(player.getUniqueId());
    if (menuId == null) return null;
    return guis.get(menuId);
  }

  public static boolean isInMenu(Player player) {
    return actives.containsKey(player.getUniqueId());
  }

  public static boolean isLoaded() {
    return listener != null;
  }

  public static void tryToLoadFor(Plugin plugin) {
    if (listener == null) {
      listener = new MagicGUIListener();
      Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
  }

  public static void tryToUnload() {
    if (listener != null) {
      HandlerList.unregisterAll(listener);
    }
  }

  private static MagicGUIListener getListener() {
    return listener;
  }

  public void addItem(ItemStack itemStack) {
    addItem(itemStack, (player, gui, slot, type) -> {
    });
  }

  public void addItem(ItemStack itemStack, ClickAction clickAction) {
    int slot = actions.size();
    if (slot < size) {
      actions.put(slot, clickAction);
      inventory.setItem(slot, itemStack);
    }
  }

  public void setItem(int slot, ItemStack itemStack) {
    setItem(slot, itemStack, (player, gui, slot1, type) -> {
    });
  }

  public void setItem(int slot, ItemStack itemStack, ClickAction clickAction) {
    if (slot < size) {
      actions.put(slot, clickAction);
      inventory.setItem(slot, itemStack);
    }
  }

  public void open(Player player) {
    player.openInventory(inventory);
    processOpen(player);
  }

  public void open(Player... players) {
    for (Player var : players) {
      open(var);
    }
  }

  public void openForAll() {
    for (Player var : Bukkit.getOnlinePlayers()) {
      open(var);
    }
  }

  public void closeForAll() {
    for (Player var : viewers) {
      close(var);
    }
  }

  public void close(Player... players) {
    for (Player var : players) {
      close(var);
    }
  }

  public void close(Player player) {
    player.closeInventory();
    processClose(player);
    if (viewers.isEmpty() && autoRemove) {
      guis.remove(this.uuid);
    }
  }

  public void unregister() {
    guis.remove(this.uuid);
  }

  public void update() {
    for (Player var : viewers) {
      var.updateInventory();
    }
  }

  private void processClick(Player player, ClickType clickType, int slot) {
    ClickAction action = actions.get(slot);
    if (action != null) {
      action.onClick(player, this, slot, clickType);
    }
  }

  private void processOpen(Player player) {
    actives.put(player.getUniqueId(), this.uuid);
    viewers.add(player);
  }

  private void processClose(Player player) {
    actives.remove(player.getUniqueId());
    viewers.remove(player);
  }

  public Map<Integer, ClickAction> getActions() {
    return actions;
  }

  public Set<Player> getViewers() {
    return viewers;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public String getTitle() {
    return title;
  }

  public boolean isAutoRemove() {
    return autoRemove;
  }

  public void setAutoRemove(boolean autoRemove) {
    this.autoRemove = autoRemove;
  }

  public int getSize() {
    return size;
  }

  public interface ClickAction {
    void onClick(Player player, MagicGUI gui, int slot, ClickType type);
  }

  private static class MagicGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
      UUID clicker = e.getWhoClicked().getUniqueId();
      if (actives.containsKey(clicker)) {
        UUID menu = actives.get(clicker);
        MagicGUI gui = guis.get(menu);
        if (gui != null) {
          e.setCancelled(true);
          gui.processClick((Player) e.getWhoClicked(), e.getClick(), e.getRawSlot());
        } else {
          actives.remove(clicker);
        }
      }
    }

    @EventHandler
    public void onQuit(InventoryCloseEvent e) {
      UUID closer = e.getPlayer().getUniqueId();
      if (actives.containsKey(closer)) {
        UUID menu = actives.remove(closer);
        MagicGUI gui = guis.get(menu);
        if (gui != null) {
          gui.processClose((Player) e.getPlayer());
        }
      }
    }

  }

}