package com.gmail.bschoe322.chatmod;

import com.gmail.bschoe322.chatmod.commands.CommandManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ChatMod extends JavaPlugin implements Listener {
  HashMap<UUID, Integer> cooldownTime = new HashMap<>(); // cannot final
  HashMap<UUID, BukkitRunnable> cooldownTask = new HashMap<>(); // cannot final
  ConfigManager configManager;

  public void onEnable() {
    register();
    Objects.requireNonNull(getCommand("chatmod")).setExecutor(new CommandManager(this));
    configManager = ConfigManager.init(this);
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    reloadConfig();
  }

  private void register() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new ChatCooldown(this), this);
    pm.registerEvents(this, this);
  }

  @EventHandler
  private void onChat(AsyncPlayerChatEvent event) {
    if (!getConfig().getBoolean("enabled")) return;

    Player sender = event.getPlayer();
    String message = event.getMessage();
    for (String word : message.split(" ")) {
      for (Map<String, String> badWord : configManager.getBadwords()) {
        final String bad = badWord.values().toArray()[0].toString();
        if (badWord.containsKey(word)) {
          if (configManager.useReplacements()) {
            if (!sender.hasPermission("chatspam.bypass")) {
              message = message.replaceAll(word, bad);
              event.setMessage(message);
              return;
            }
          }
          sender.sendMessage(
              ChatColor.translateAlternateColorCodes(
                  '&', Objects.requireNonNull(getConfig().getString("badwords_message"))));
          event.setCancelled(true);
          return;
        }
        if (!configManager.detectSimilarities()) {
          sender.sendMessage(
              ChatColor.translateAlternateColorCodes(
                  '&', Objects.requireNonNull(getConfig().getString("badwords_message"))));
          event.setCancelled(true);
          return;
        } else {
          final double simScore = StringUtils.getLevenshteinDistance(bad, word);
          if (simScore <= 0.1) {
            message = message.replaceAll(word, bad);
            event.setMessage(message);
            return;
          }
        }
      }
    }
    event.setMessage(event.getMessage());
  }
}
