package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@Command("fogata")
@Permission("fogata")
public class FogataCommand {
  public static final NamespacedKey pos1Key = new NamespacedKey(DedsafioPlugin.getInstance(), "pos1");
  public static final NamespacedKey pos2Key = new NamespacedKey(DedsafioPlugin.getInstance(), "pos2");
  private static final String helpMessage = "&7Para definir una fogata, deberás usar el comando &f/fogata set&7 esto te dará un &fitem&7 con el cual puedes seleccionar el perimetro dónde se encuentra tu fogata";

  @Default
  public static void fogata(CommandSender sender) {
    ChatUtil.sendMessage(sender, new String[]{
      "&7/fogata set",
      "&7/fogata remove",
      "&7/fogata apply",
      "&7/fogata help"
    });
  }

  @Subcommand("help")
  public static void help(CommandSender sender) {
    ChatUtil.sendMessage(sender, new String[]{
      ChatUtil.NORMAL_LINE, helpMessage, ChatUtil.NORMAL_LINE,
    });
  }

  @Subcommand("set")
  public static void set(CommandSender sender) {
    Player player = (Player) sender;
    CustomItem customItem = ItemManager.getItem("marker_item");
    if (customItem == null) {
      ChatUtil.sendMessage(sender, "&cEl item marker_item no existe");
      return;
    }

    player.getInventory().addItem(customItem.getItemStack());
    ChatUtil.sendMessage(sender, new String[]{
      "&7Se te ha dado un item con el cual podrás definir el perimetro dónde se encuentra la fogata",
      "&7Cuando hayas seleccionado el perimetro en el que quieres que se active la fogata, usa el comando &7/fogata apply"
    });
  }

  @Subcommand("apply")
  public static void apply(CommandSender sender) {
    Player player = (Player) sender;

    CustomItem customItem = ItemManager.getItem("marker_item");
    ItemStack itemStack = ItemManager.getItemInList(player.getInventory().getContents(), customItem);

    if (itemStack == null) {
      ChatUtil.sendMessage(sender, "&cPara usar este comando deberás haber usado el comando &7/fogata set");
      return;
    }

    World world = player.getWorld();
    Location pos1 = getLocationFromItem(world, itemStack, pos1Key);
    Location pos2 = getLocationFromItem(world, itemStack, pos2Key);
    DedsafioPlugin plugin = DedsafioPlugin.getInstance();

    if (pos1 == null || pos2 == null) {
      ChatUtil.sendMessage(sender, "&cNo has definido la primera o segunda ubicación");
      return;
    }

    String positionOne = pos1.getBlockX() + "," + pos1.getBlockY() + "," + pos1.getBlockZ();
    String positionTwo = pos2.getBlockX() + "," + pos2.getBlockY() + "," + pos2.getBlockZ();

    plugin.db.set("fogata-pos-one", positionOne);
    plugin.db.set("fogata-pos-two", positionTwo);

    player.getInventory().remove(itemStack);
    ChatUtil.sendMessage(sender, new String[]{
      "&aFogata activada correctamente",
      "&7Para desactivarla, usa el comando &c/fogata remove"
    });

    plugin.db.save();
  }

  private static Location getLocationFromItem(World world, ItemStack itemStack, NamespacedKey key) {
    if (itemStack == null || itemStack.getItemMeta() == null) return null;

    PersistentDataContainer data = itemStack.getItemMeta().getPersistentDataContainer();
    if (!data.has(key, PersistentDataType.STRING)) return null;

    String[] parts = Objects.requireNonNull(data.get(key, PersistentDataType.STRING)).split(",");
    int x = Integer.parseInt(parts[0]);
    int y = Integer.parseInt(parts[1]);
    int z = Integer.parseInt(parts[2]);

    return new Location(world, x, y, z);
  }
}
