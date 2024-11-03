package cc.atenea.dedsafioUtils.features;

import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class TimeController {

  private final Plugin plugin;
  private final BossBar closeAlertBossBar;
  private BukkitRunnable scheduleTask;
  private BukkitRunnable bossBarTask;

  public TimeController(Plugin plugin) {
    this.plugin = plugin;
    this.closeAlertBossBar = Bukkit.createBossBar(
      ChatUtil.translate(ConfigResource.TimeControllerBossbarTitle),
      BarColor.RED,
      BarStyle.SOLID
    );

    if (ConfigResource.TimeControllerEnabled) {
      startScheduleTask();
    }
  }

  private void startScheduleTask() {
    scheduleTask = new BukkitRunnable() {
      @Override
      public void run() {
        LocalTime now = LocalTime.now();

        LocalTime openTime = LocalTime.of(
          Integer.parseInt(ConfigResource.TimeControllerOpenServer.split(":")[0]),
          Integer.parseInt(ConfigResource.TimeControllerOpenServer.split(":")[1])
        );

        LocalTime closeTime = LocalTime.of(
          Integer.parseInt(ConfigResource.TimeControllerCloseServer.split(":")[0]),
          Integer.parseInt(ConfigResource.TimeControllerCloseServer.split(":")[1])
        );

        if (now.isAfter(openTime) && now.isBefore(closeTime)) {
          checkCloseTime(closeTime);
        } else if (now.isAfter(closeTime) && !now.isAfter(closeTime.plusSeconds(1))) {
          kickPlayersAndEnableWhitelist();
        }
      }
    };

    scheduleTask.runTaskTimer(plugin, 0, 20L * 60);
  }

  private void checkCloseTime(LocalTime closeTime) {
    LocalTime now = LocalTime.now();
    long secondsUntilClose = TimeUnit.MINUTES.toSeconds(closeTime.getMinute() - now.getMinute())
      + closeTime.getSecond() - now.getSecond();

    if (secondsUntilClose > 0 && secondsUntilClose <= ConfigResource.TimeControllerBossbarIn) {
      startBossBarTask(secondsUntilClose);
    }
  }

  private void startBossBarTask(long initialSeconds) {
    if (bossBarTask != null && !bossBarTask.isCancelled()) {
      bossBarTask.cancel();
    }

    bossBarTask = new BukkitRunnable() {
      long secondsLeft = initialSeconds;

      @Override
      public void run() {
        if (secondsLeft <= 0) {
          closeAlertBossBar.removeAll();
          this.cancel();
          return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
          if (!closeAlertBossBar.getPlayers().contains(player)) {
            closeAlertBossBar.addPlayer(player);
          }
        }

        String bossbarTitle = ConfigResource.TimeControllerBossbarTitle
          .replace("{seconds}", String.valueOf(secondsLeft));

        closeAlertBossBar.setTitle(ChatUtil.translate(bossbarTitle));
        closeAlertBossBar.setProgress((double) secondsLeft / ConfigResource.TimeControllerBossbarIn);

        secondsLeft--;
      }
    };

    bossBarTask.runTaskTimer(plugin, 0, 20L);
  }

  private void kickPlayersAndEnableWhitelist() {
    Server server = Bukkit.getServer();
    server.setWhitelist(true);

    for (Player player : server.getOnlinePlayers()) {
      if (!player.isOp() && !player.hasPermission(ConfigResource.TimeControllerBypassPermission)) {
        player.kickPlayer(ConfigResource.TimeControllerKickMessage);
      }
    }

    closeAlertBossBar.removeAll();
  }

  public void stop() {
    if (scheduleTask != null) {
      scheduleTask.cancel();
    }

    if (bossBarTask != null) {
      bossBarTask.cancel();
    }

    closeAlertBossBar.removeAll();
  }
}
