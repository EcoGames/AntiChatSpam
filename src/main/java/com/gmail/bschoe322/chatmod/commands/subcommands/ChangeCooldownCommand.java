package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChangeCooldownCommand extends SubCommand {
  @Override
  public String getName() {
    return "changecooldown";
  }

  @Override
  public String getPermission() {
    return "chatmod.changecooldown";
  }

  @Override
  public String getDescription() {
    return "Change the cooldown time";
  }

  @Override
  public String getSyntax() {
    return "/cm changecooldown <time>";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 2) {
      final String time = args[1];
      final int timeInteger = Integer.parseInt(time);
      ConfigManager.getInstance().changeCooldownTime(timeInteger);
      sender.sendMessage(MessagesUtil.PREFIX + ChatColor.GREEN + "Chat cooldown is now " + args[1] + " second(s)!");
      sender.sendMessage(MessagesUtil.PREFIX + MessagesUtil.RELOAD_CONFIG);
    } else {
      sender.sendMessage(getSyntax());
    }
  }
}
