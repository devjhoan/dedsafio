package cc.atenea.dedsafioUtils.commands;

import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.AGreedyStringArgument;
import org.bukkit.command.CommandSender;

@Command("eon")
@Permission("eon")
public class EonCommand {
  @Default()
  public static void run(CommandSender sender, @AGreedyStringArgument String text) {
    ChatUtil.broadcast(new String[]{
      "&a&l▶ Eón:&r",
      "",
      text
    });
  }
}
