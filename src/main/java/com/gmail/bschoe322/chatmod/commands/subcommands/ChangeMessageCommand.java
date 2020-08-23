package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChangeMessageCommand extends SubCommand {
  @Override
  public String getName() {
    return "changemessage";
  }

  @Override
  public String getPermission() {
    return "chatmod.changemessage";
  }

  @Override
  public String getDescription() {
    return "Changes message when replacements are off";
  }

  @Override
  public String getSyntax() {
    return "/cm changemessage <message>";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 1) {
      sender.sendMessage(MessagesUtil.ERROR + "Please specify a message.");
      return;
    }
    StringBuilder str = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      str.append(args[i]).append(" ");
    }
    String msg = str.toString();
    ConfigManager.getInstance().changeMessage(msg);
    sender.sendMessage(
        MessagesUtil.PREFIX
            + ChatColor.GREEN
            + "You set the sender message to: "
            + ChatColor.translateAlternateColorCodes('&', msg)
            + ChatColor.GREEN
            + "!");
  }
}
