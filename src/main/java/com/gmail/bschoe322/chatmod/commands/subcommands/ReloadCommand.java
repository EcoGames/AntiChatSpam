package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
  @Override
  public String getName() {
    return "reload";
  }

  @Override
  public String getPermission() {
    return "chatmod.reload";
  }

  @Override
  public String getDescription() {
    return "Reloads the config";
  }

  @Override
  public String getSyntax() {
    return "/cm reload";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    ConfigManager.getInstance().reloadConfig();
    sender.sendMessage(MessagesUtil.PREFIX + ChatColor.GREEN + "Reloaded config");
  }
}
