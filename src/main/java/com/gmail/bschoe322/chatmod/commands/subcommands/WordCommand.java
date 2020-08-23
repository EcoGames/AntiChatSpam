package com.gmail.bschoe322.chatmod.commands.subcommands;

import com.gmail.bschoe322.chatmod.ChatMod;
import com.gmail.bschoe322.chatmod.ConfigManager;
import com.gmail.bschoe322.chatmod.MessagesUtil;
import com.gmail.bschoe322.chatmod.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class WordCommand extends SubCommand {
  private final ChatMod plugin;

  public WordCommand(ChatMod plugin) {
    this.plugin = plugin;
  }

  @Override
  public String getName() {
    return "word";
  }

  @Override
  public String getPermission() {
    return null;
  }

  @Override
  public String getDescription() {
    return "Base command to list, remove, or add words";
  }

  @Override
  public String getSyntax() {
    return "/cm word <add|remove|list>";
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length < 2) {
      sender.sendMessage(ChatColor.RED + getSyntax());
      return;
    }
    String path;
    final List<Map<String, String>> words = ConfigManager.getInstance().getBadwords();
    switch (args[1]) {
      case "add":
        if (!sender.hasPermission("chatmod.word.manage")) {
          sender.sendMessage(
              MessagesUtil.ERROR
                  + "You don't have permission '"
                  + ChatColor.BOLD
                  + "chatmod.word.manage"
                  + ChatColor.RED
                  + "'!");
          return;
        }
        if (args.length != 4) {
          sender.sendMessage(MessagesUtil.ERROR + "/cm word add <word> <replacement>");
          return;
        }
        if (!plugin.getConfig().isSet("badwords." + args[2])) {
          ConfigManager.getInstance().addBadWord(args[2], args[3]);
          sender.sendMessage(
              MessagesUtil.PREFIX
                  + ChatColor.GREEN
                  + ChatColor.BOLD
                  + args[3]
                  + ChatColor.GREEN
                  + " will now replace "
                  + ChatColor.RED
                  + ChatColor.BOLD
                  + args[2]);
          sender.sendMessage(MessagesUtil.PREFIX + MessagesUtil.RELOAD_CONFIG);
        } else {
          sender.sendMessage(
              MessagesUtil.PREFIX
                  + ChatColor.RED
                  + "Bad word '"
                  + ChatColor.BOLD
                  + args[2]
                  + "' already exists!");
        }
        break;
      case "remove":
        if (!sender.hasPermission("chatmod.word.manage")) {
          sender.sendMessage(
              MessagesUtil.ERROR
                  + "You don't have permission '"
                  + ChatColor.BOLD
                  + "chatmod.word.manage"
                  + ChatColor.RED
                  + "'!");
          return;
        }
        if (args.length != 3) {
          sender.sendMessage(MessagesUtil.ERROR + "/cm word remove <bad word>");
          return;
        }
        path = "badwords." + args[2];
        if (plugin.getConfig().isSet(path)) {
          plugin.getConfig().set(path, null);
          plugin.saveConfig();
          sender.sendMessage(
              MessagesUtil.PREFIX
                  + ChatColor.GREEN
                  + "Bad word removed: "
                  + ChatColor.BOLD
                  + args[2]);
          sender.sendMessage(MessagesUtil.PREFIX + MessagesUtil.RELOAD_CONFIG);
        } else {
          sender.sendMessage(
              MessagesUtil.PREFIX
                  + ChatColor.RED
                  + "Bad word '"
                  + ChatColor.BOLD
                  + args[2]
                  + "' doesn't exist!");
        }
        break;
      case "list":
        if (!sender.hasPermission("chatmod.word.list")) {
          sender.sendMessage(
              MessagesUtil.ERROR
                  + "You don't have permission '"
                  + ChatColor.BOLD
                  + "chatmod.word.list"
                  + ChatColor.RED
                  + "'!");
          return;
        }
        sender.sendMessage("Bad word - Replacement");
        for (Map<String, String> word : words) {
          final String badWord = word.keySet().toArray()[0].toString();
          final String replacement = word.values().toArray()[0].toString();
          sender.sendMessage(badWord + " - " + replacement);
        }
        break;
    }
  }
}
