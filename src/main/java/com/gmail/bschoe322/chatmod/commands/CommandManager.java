package com.gmail.bschoe322.chatmod.commands;

import com.gmail.bschoe322.chatmod.ChatMod;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.subcommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
  private final ArrayList<SubCommand> subCommands = new ArrayList<>();

  public CommandManager(ChatMod plugin) {
    subCommands.add(new WordCommand(plugin));
    subCommands.add(new ReloadCommand());
    subCommands.add(new ToggleCommand());
    subCommands.add(new ToggleReplacementsCommand());
    subCommands.add(new ChangeMessageCommand());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;

      if (args.length > 0) {
        for (SubCommand subCommand : getSubCommands()) {
          if (args[0].equalsIgnoreCase(subCommand.getName())) {
            final String permission = subCommand.getPermission();
            if (permission != null) {
              if (!p.hasPermission(permission)) {
                p.sendMessage(
                    MessagesUtil.ERROR
                        + "You don't have permission '"
                        + ChatColor.BOLD
                        + permission
                        + ChatColor.RED
                        + "'!");
                return true;
              }
            }
            subCommand.perform(p, args);
            return true;
          }
        }
      }
      p.sendMessage(ChatColor.GOLD + "---- AntiChatSpam Help ----");

      for (SubCommand subCommand : getSubCommands()) {
        p.sendMessage(
            ChatColor.GOLD
                + ChatColor.BOLD.toString()
                + subCommand.getSyntax()
                + ChatColor.GOLD
                + " - "
                + subCommand.getDescription());
      }

      p.sendMessage(ChatColor.GOLD + "------------------------");
    }

    return true;
  }

  public ArrayList<SubCommand> getSubCommands() {
    return subCommands;
  }
}
