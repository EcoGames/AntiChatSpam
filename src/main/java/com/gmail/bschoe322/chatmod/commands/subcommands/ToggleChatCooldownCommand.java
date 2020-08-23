package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import sun.misc.MessageUtils;

public class ToggleChatCooldownCommand extends SubCommand {
  @Override
  public String getName() {
    return "togglechatcooldown";
  }

  @Override
  public String getPermission() {
    return "chatmod.togglechatcooldown";
  }

  @Override
  public String getDescription() {
    return "Toggles that chat cooldown functionality.";
  }

  @Override
  public String getSyntax() {
    return "/cm togglechatcooldown";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    final boolean toggled = ConfigManager.getInstance().toggleChatCooldown();
    if (toggled) {
      sender.sendMessage(MessagesUtil.PREFIX + ChatColor.GREEN + "Chat cooldown is enabled!");
    } else {
      sender.sendMessage(MessagesUtil.ERROR + "Chat cooldown is disabled!");
    }
    sender.sendMessage(MessagesUtil.RELOAD_CONFIG);
  }
}
