package com.gmail.bschoe322.chatmod;

import org.bukkit.ChatColor;

public class MessagesUtil {
  public static String PREFIX =
      ChatColor.RED + "AntiChatSpam" + ChatColor.BOLD + " >> " + ChatColor.RESET;
  public static String RELOAD_CONFIG =
      ChatColor.GREEN + "Please reload the config by running /cm reload.";
  public static String ERROR = PREFIX + ChatColor.RED;
}
