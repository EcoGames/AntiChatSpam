package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ToggleReplacementsCommand extends SubCommand {
  @Override
  public String getName() {
    return "togglereplacements";
  }

  @Override
  public String getPermission() {
    return "chatmod.togglereplacements";
  }

  @Override
  public String getDescription() {
    return "Toggles using replacements";
  }

  @Override
  public String getSyntax() {
    return "/cm togglereplacements";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    final boolean isEnabled = ConfigManager.getInstance().toggleReplacements();
    if (isEnabled) {
      sender.sendMessage(MessagesUtil.PREFIX + ChatColor.GREEN + "Replacements are enabled");
    } else {
      sender.sendMessage(MessagesUtil.ERROR + "Replacements are disabled");
    }
    sender.sendMessage(MessagesUtil.PREFIX + MessagesUtil.RELOAD_CONFIG);
  }
}
