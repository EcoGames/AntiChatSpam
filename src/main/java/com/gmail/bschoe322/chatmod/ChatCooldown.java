package com.gmail.bschoe322.chatmod;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ChatCooldown implements Listener {
  ChatMod plugin;
  ConfigManager configManager;

  public ChatCooldown(ChatMod plugin) {
    this.plugin = plugin;
    this.configManager = ConfigManager.getInstance();
  }

  @EventHandler
  public void onChat(AsyncPlayerChatEvent e) {
    final Player plr = e.getPlayer();
    final UUID uniqueId = plr.getUniqueId();

    if (plugin.cooldownTime.containsKey(uniqueId)) {
      e.getPlayer()
          .sendMessage(
              MessagesUtil.PREFIX
                  + "Please wait"
                  + plugin.cooldownTime.get(uniqueId)
                  + " seconds before sending a message!");
      e.setCancelled(true);
      return;
    }

    if (!plr.hasPermission("chatspam.bypass") && configManager.isChatCooldownEnabled()) {
      plugin.cooldownTime.put(uniqueId, configManager.getCooldownTime());
      plugin.cooldownTask.put(
          uniqueId,
          new BukkitRunnable() {
            public void run() {
              plugin.cooldownTime.put(uniqueId, plugin.cooldownTime.get(uniqueId) - 1);
              if (plugin.cooldownTime.get(uniqueId) <= 0) {
                plugin.cooldownTime.remove(uniqueId);
                plugin.cooldownTask.remove(uniqueId);
                cancel();
              }
            }
          });
      plugin.cooldownTask.get(uniqueId).runTaskTimer(plugin, 20L, 20L);
    }
  }
}
