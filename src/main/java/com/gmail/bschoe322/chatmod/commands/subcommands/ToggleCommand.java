package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ToggleCommand extends SubCommand {
  @Override
  public String getName() {
    return "toggle";
  }

  @Override
  public String getPermission() {
    return "chatmod.plugin";
  }

  @Override
  public String getDescription() {
    return "Toggles the plugin on/off";
  }

  @Override
  public String getSyntax() {
    return "/cm toggle";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    final boolean isEnabled = ConfigManager.getInstance().togglePlugin();
    if (isEnabled) {
      sender.sendMessage(MessagesUtil.PREFIX + ChatColor.GREEN + "Plugin is enabled");
    } else {
      sender.sendMessage(MessagesUtil.ERROR + "Plugin is disabled");
    }
    sender.sendMessage(MessagesUtil.PREFIX + MessagesUtil.RELOAD_CONFIG);
  }
}
