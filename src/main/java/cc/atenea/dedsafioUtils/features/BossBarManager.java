package cc.atenea.dedsafioUtils.features;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossBarManager {
  private final Map<String, BossBarTimer> timers = new HashMap<>();

  public boolean addTimer(int seconds, String name, String color, String style) {
    if (timers.containsKey(name)) {
      return false;
    }

    BossBarTimer timer = new BossBarTimer(seconds, name, color, style);
    timers.put(name, timer);

    timer.start();
    return true;
  }

  public void removeTimer(String name) {
    timers.remove(name);
  }

  public boolean stopTimer(String name) {
    BossBarTimer timer = timers.get(name);
    if (timer == null) {
      return false;
    }

    timer.stop();
    return true;
  }
}

class BossBarTimer {
  private final int seconds;
  private final DedsafioPlugin plugin = DedsafioPlugin.getInstance();
  public BukkitRunnable bossBarTask;

  final BossBar customBossBar;
  final String name;

  public BossBarTimer(int seconds, String name, String color, String style) {
    this.seconds = seconds;
    this.name = name;

    this.customBossBar = Bukkit.createBossBar(
      ChatUtil.translate(ConfigResource.TimeControllerBossbarTitle),
      getColor(color),
      getStyle(style)
    );
  }

  public void start() {
    bossBarTask = new BukkitRunnable() {
      long secondsLeft = seconds;

      @Override
      public void run() {
        if (secondsLeft <= 0) {
          stop();
          return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
          if (!customBossBar.getPlayers().contains(player)) {
            customBossBar.addPlayer(player);
          }
        }

        customBossBar.setTitle(ChatUtil.translate("&c" + name + " &7(" + secondsLeft + "s)"));
        customBossBar.setProgress((double) secondsLeft / seconds);

        secondsLeft--;
      }
    };

    bossBarTask.runTaskTimer(DedsafioPlugin.getInstance(), 0, 20L);
  }

  public void stop() {
    bossBarTask.cancel();
    customBossBar.removeAll();
    plugin.bossBarManager.removeTimer(name);
  }

  private BarColor getColor(String color) {
    return switch (color) {
      case "pink" -> BarColor.PINK;
      case "blue" -> BarColor.BLUE;
      case "green" -> BarColor.GREEN;
      case "yellow" -> BarColor.YELLOW;
      case "purple" -> BarColor.PURPLE;
      case "white" -> BarColor.WHITE;
      default -> BarColor.RED;
    };
  }

  private BarStyle getStyle(String style) {
    return switch (style) {
      case "6" -> BarStyle.SEGMENTED_6;
      case "10" -> BarStyle.SEGMENTED_10;
      case "12" -> BarStyle.SEGMENTED_12;
      case "20" -> BarStyle.SEGMENTED_20;
      default -> BarStyle.SOLID;
    };
  }
}

