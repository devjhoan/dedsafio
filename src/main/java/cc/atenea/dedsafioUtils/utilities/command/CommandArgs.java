package cc.atenea.dedsafioUtils.utilities.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class CommandArgs {
  private final CommandSender sender;
  private final String[] args;

  protected CommandArgs(CommandSender sender, org.bukkit.command.Command command, String label, String[] args, int subCommand) {
    String[] modArgs = new String[args.length - subCommand];

    if (args.length - subCommand >= 0) System.arraycopy(args, subCommand, modArgs, 0, args.length - subCommand);

    StringBuilder buffer = new StringBuilder();
    buffer.append(label);

    for (int x = 0; x < subCommand; x++) {
      buffer.append(".").append(args[x]);
    }

    String cmdLabel = buffer.toString();
    this.sender = sender;
    this.args = modArgs;
  }

  public String[] getArgs() {
    return args;
  }

  public int length() {
    return args.length;
  }

  public boolean isPlayer() {
    return sender instanceof Player;
  }

  public Player getPlayer() {
    if (sender instanceof Player) {
      return (Player) sender;
    } else {
      return null;
    }
  }

  public CommandSender getSender() {
    return sender;
  }
}