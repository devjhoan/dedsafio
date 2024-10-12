package cc.atenea.dedsafioUtils.events;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
  private final DedsafioPlugin plugin;

  public DeathEvent(DedsafioPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  private void onDeath(PlayerDeathEvent event) {
    event.setDeathMessage(null);

    Player player = event.getEntity();
    plugin.sendConsoleCommand("tag " + player.getName() + " remove anim.*");
    plugin.sendConsoleCommand("tag @a add anim.muerte");

    String message = "&c&l" + player.getName() + " has died!";

    for (Player p : player.getWorld().getPlayers()) {
      sendTimedActionBar(p, message, 15);
    }
  }

  public void sendTimedActionBar(Player player, String message, int durationInSeconds) {
    sendActionBar(player, ChatUtil.translate(message));

    Bukkit.getScheduler().runTaskLater(plugin, () -> {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
    }, durationInSeconds * 20L);
  }

  public void sendActionBar(Player player, String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
  }
}
