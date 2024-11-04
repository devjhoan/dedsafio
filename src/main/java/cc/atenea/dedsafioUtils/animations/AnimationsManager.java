package cc.atenea.dedsafioUtils.animations;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.AnimationResource;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Collection;
import java.util.List;

public class AnimationsManager {
  public static List<String> MUERTE;
  public static List<String> RULETA_RED;
  public static List<String> RULETA_ORANGE;
  public static List<String> RULETA_YELLOW;
  public static List<String> RULETA_GREEN;
  public static List<String> RULETA_BLUE;
  public static List<String> RULETA_PURPLE;
  public static List<String> RULETA_PINK;
  public static List<String> RULETA_CYAN;
  public static List<String> NUTRIA;
  public static List<String> REVIIL;
  private final DedsafioPlugin plugin;
  private final AnimationResource animationResource;

  public AnimationsManager(DedsafioPlugin plugin) {
    this.plugin = plugin;
    this.animationResource = new AnimationResource(plugin);

    reload();
  }

  public void reload() {
    RULETA_RED = animationResource.getAnimation("red");
    RULETA_ORANGE = animationResource.getAnimation("orange");
    RULETA_YELLOW = animationResource.getAnimation("yellow");
    RULETA_GREEN = animationResource.getAnimation("green");
    RULETA_BLUE = animationResource.getAnimation("blue");
    RULETA_PURPLE = animationResource.getAnimation("purple");
    RULETA_PINK = animationResource.getAnimation("pink");
    RULETA_CYAN = animationResource.getAnimation("cyan");
    MUERTE = animationResource.getAnimation("muerte");
    NUTRIA = animationResource.getAnimation("nutria");
    REVIIL = animationResource.getAnimation("reviil");
  }

  public void playAnimation(Player player, Animations animation, String positionType, String sound) {
    List<String> frames = enumToAnimation(animation);
    AnimationPosition position = getAnimationPosition(positionType);
    if (frames == null) return;

    if (sound != null) {
      player.playSound(player.getLocation(), sound, 4.0F, 1.0F);
    }

    new BukkitRunnable() {
      int currentFrame = 0;

      @Override
      public void run() {
        if (currentFrame >= frames.size()) {
          this.cancel();
          return;
        }

        if (position == AnimationPosition.Scoreboard) {
          showFrameInScoreboard(player, frames.get(currentFrame), currentFrame >= frames.size() - 1);
        } else if (position == AnimationPosition.ActionBar) {
          showFrameInActionBar(player, frames.get(currentFrame), currentFrame >= frames.size() - 1);
        } else if (position == AnimationPosition.Title) {
          showFrameInTitle(player, frames.get(currentFrame), currentFrame >= frames.size() - 1);
        } else if (position == AnimationPosition.SubTitle) {
          showFrameInSubTitle(player, frames.get(currentFrame), currentFrame >= frames.size() - 1);
        }

        currentFrame++;
      }
    }.runTaskTimer(plugin, 0L, 1L);
  }

  public void playAnimation(Animations animation, String positionType, String sound) {
    List<String> frames = enumToAnimation(animation);
    AnimationPosition position = getAnimationPosition(positionType);
    if (frames == null) return;

    Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
    if (sound != null) {
      players.forEach(player -> player.playSound(player.getLocation(), sound, 4.0F, 1.0F));
    }

    new BukkitRunnable() {
      int currentFrame = 0;

      @Override
      public void run() {
        if (currentFrame >= frames.size()) {
          this.cancel();
          return;
        }

        boolean clearScreen = currentFrame >= frames.size() - 1;

        if (position == AnimationPosition.Scoreboard) {
          players.forEach(player -> showFrameInScoreboard(player, frames.get(currentFrame), clearScreen));
        } else if (position == AnimationPosition.Title) {
          players.forEach(player -> showFrameInTitle(player, frames.get(currentFrame), clearScreen));
        } else if (position == AnimationPosition.ActionBar) {
          players.forEach(player -> showFrameInActionBar(player, frames.get(currentFrame), clearScreen));
        } else if (position == AnimationPosition.SubTitle) {
          players.forEach(player -> showFrameInSubTitle(player, frames.get(currentFrame), clearScreen));
        }

        currentFrame++;
      }
    }.runTaskTimer(plugin, 0L, 1L);
  }

  private void showFrameInTitle(Player player, String frame, boolean clear) {
    player.sendTitle(String.valueOf(frame), "", 0, 20, 0);
    if (clear) {
      player.sendTitle(" ", "", 0, 20, 0);
    }
  }

  private void showFrameInSubTitle(Player player, String frame, boolean clear) {
    player.sendTitle("", String.valueOf(frame), 0, 20, 0);
    if (clear) {
      player.sendTitle("", " ", 0, 20, 0);
    }
  }

  private void showFrameInActionBar(Player player, String frame, boolean clear) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.valueOf(frame)));
    if (clear) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
    }
  }

  public void showFrameInScoreboard(Player player, String frame, boolean clear) {
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    assert manager != null;
    Scoreboard scoreboard = manager.getNewScoreboard();

    if (clear) {
      player.setScoreboard(scoreboard);
      return;
    }

    Objective objective = scoreboard.registerNewObjective("animation", Criteria.DUMMY, " ");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    Score score = objective.getScore(frame);
    score.setScore(0);

    player.setScoreboard(scoreboard);
  }

  private List<String> enumToAnimation(Animations animation) {
    return switch (animation) {
      case RuletaRed -> RULETA_RED;
      case RuletaOrange -> RULETA_ORANGE;
      case RuletaYellow -> RULETA_YELLOW;
      case RuletaGreen -> RULETA_GREEN;
      case RuletaBlue -> RULETA_BLUE;
      case RuletaPurple -> RULETA_PURPLE;
      case RuletaPink -> RULETA_PINK;
      case RuletaCyan -> RULETA_CYAN;
      case Muerte -> MUERTE;
      case Nutria -> NUTRIA;
      case Reviil -> REVIIL;
    };
  }

  private AnimationPosition getAnimationPosition(String position) {
    return switch (position) {
      case "actionbar" -> AnimationPosition.ActionBar;
      case "sidebar" -> AnimationPosition.Scoreboard;
      case "subtitle" -> AnimationPosition.SubTitle;
      default -> AnimationPosition.Title;
    };
  }
}
