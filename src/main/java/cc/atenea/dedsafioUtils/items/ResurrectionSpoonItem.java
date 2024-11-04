package cc.atenea.dedsafioUtils.items;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.items.core.Clickable;
import cc.atenea.dedsafioUtils.items.core.CustomItem;
import cc.atenea.dedsafioUtils.items.core.ItemSettings;
import cc.atenea.dedsafioUtils.menus.FogataMenu;
import cc.atenea.dedsafioUtils.providers.User;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ResurrectionSpoonItem extends CustomItem implements Clickable {
  public ResurrectionSpoonItem() {
    super(
      new ItemSettings(Material.BOOK, "resurrection_spoon")
        .setDisplayName("&6&lCuchara de resurrección").setItemId(2)
    );
  }

  @Override
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    DedsafioPlugin plugin = DedsafioPlugin.getInstance();
    User userProfile = plugin.userManager.getUser(player);

    String defaultPosition = "0,0,0";
    String posOne = (String) plugin.db.get("fogata-pos-one");
    String posTwo = (String) plugin.db.get("fogata-pos-two");

    if (posOne == null || posTwo == null || posOne.equals(defaultPosition) || posTwo.equals(defaultPosition)) {
      ChatUtil.sendMessage(player, "&cNo has definido la primer o segunda ubicación");
      return;
    }

    Location targetLocation = null;
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
      targetLocation = event.getClickedBlock().getLocation();
    } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
      Block targetBlock = player.getTargetBlockExact(5);
      if (targetBlock != null) {
        targetLocation = targetBlock.getLocation();
      }
    }

    if (targetLocation == null) {
      targetLocation = player.getLocation();
    }

    Location posOneLocation = parseLocationFromString(posOne, player.getWorld());
    Location posTwoLocation = parseLocationFromString(posTwo, player.getWorld());

    if (!isWithinBounds(targetLocation, posOneLocation, posTwoLocation)) {
      ChatUtil.sendActionBar(player, "&cDebes estar cerca de la fogata de resurrección");
      return;
    }

    if (!userProfile.hasSoul()) {
      ChatUtil.sendActionBar(player, "&cDebes tener una alma para poder resucitar a un jugador");
      return;
    }

    openMenu(player);
  }

  private boolean isWithinBounds(Location loc, Location posOne, Location posTwo) {
    double xMin = Math.min(posOne.getX(), posTwo.getX());
    double xMax = Math.max(posOne.getX(), posTwo.getX());
    double yMin = Math.min(posOne.getY(), posTwo.getY());
    double yMax = Math.max(posOne.getY(), posTwo.getY());
    double zMin = Math.min(posOne.getZ(), posTwo.getZ());
    double zMax = Math.max(posOne.getZ(), posTwo.getZ());

    return loc.getX() >= xMin && loc.getX() <= xMax
      && loc.getY() >= yMin && loc.getY() <= yMax
      && loc.getZ() >= zMin && loc.getZ() <= zMax;
  }

  private void openMenu(Player player) {
    FogataMenu menu = new FogataMenu();
    menu.open(player);
  }

  private Location parseLocationFromString(String locationString, World world) {
    String[] parts = locationString.split(",");
    return new Location(world, Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
  }
}
