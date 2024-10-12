package cc.atenea.dedsafioUtils.commands.animations;

import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.command.BaseCommand;
import cc.atenea.dedsafioUtils.utilities.command.Command;
import cc.atenea.dedsafioUtils.utilities.command.CommandArgs;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import org.bukkit.command.CommandSender;

public class AnimationCommand extends BaseCommand {
  @Command(name = "animation", permission = "dedsafio.command.animation", inGameOnly = false)
  @Override
  public void onCommand(CommandArgs command) {
    CommandSender sender = command.getSender();
    String[] args = command.getArgs();

    if (args.length == 0) {
      ChatUtil.sendMessage(sender, LanguageResource.ANIMATION_COMMAND_MESSAGE_HELP);
      return;
    }
  }
}
