package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.features.BossBarManager;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AGreedyStringArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.AMultiLiteralArgument;
import org.bukkit.command.CommandSender;

@Command("timer")
@Permission("timer")
public class TimerCommand {
  @Default()
  public static void help(CommandSender sender) {
    ChatUtil.sendMessage(sender, "&7/timer add <seconds> <color> <style> <name>");
  }

  @Subcommand("add")
  public static void add(
    CommandSender sender,
    @AIntegerArgument(min = 0) int seconds,
    @AMultiLiteralArgument({"pink", "blue", "red", "green", "yellow", "purple", "white"}) String color,
    @AMultiLiteralArgument({"solid", "6", "10", "12", "20"}) String style,
    @AGreedyStringArgument String name
  ) {
    if (seconds == 0) {
      ChatUtil.sendMessage(sender, "&cSeconds must be greater than 0");
      return;
    }

    BossBarManager manager = DedsafioPlugin.getInstance().bossBarManager;
    boolean added = manager.addTimer(seconds, name, color, style);

    if (!added) {
      ChatUtil.sendMessage(sender, "&cThere is already a timer with that name");
      return;
    }

    ChatUtil.sendMessage(sender, "&aTimer was started successfully");
  }

  @Subcommand("stop")
  public static void stop(CommandSender sender, @AGreedyStringArgument String name) {
    BossBarManager manager = DedsafioPlugin.getInstance().bossBarManager;
    boolean stopped = manager.stopTimer(name);

    if (!stopped) {
      ChatUtil.sendMessage(sender, "&cThere is no timer with that name");
      return;
    }

    ChatUtil.sendMessage(sender, "&aTimer was stopped successfully");
  }
}
