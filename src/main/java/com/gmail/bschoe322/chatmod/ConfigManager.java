package com.gmail.bschoe322.chatmod;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
  private static ConfigManager instance = null;
  private final ChatMod plugin;
  private final FileConfiguration config;

  private ConfigManager(ChatMod plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfig();
  }

  public static ConfigManager getInstance() {
    if (instance == null) {
      throw new AssertionError("You have to call init() first!");
    }
    return instance;
  }

  public static synchronized ConfigManager init(ChatMod plugin) {
    if (instance != null) {
      throw new AssertionError("ConfigManager is already initialized!");
    }

    instance = new ConfigManager(plugin);
    return instance;
  }

  @SuppressWarnings("unchecked")
  public List<Map<String, String>> getBadwords() {
    return (List<Map<String, String>>) config.getList("badwords");
  }

  public boolean useReplacements() {
    return config.getBoolean("replace_words");
  }

  public boolean detectSimilarities() {
    return config.getBoolean("detect_similarities");
  }

  private void saveConfig() {
    plugin.saveConfig();
  }

  public void reloadConfig() {
    plugin.reloadConfig();
  }

  public void addBadWord(String badWord, String replacement) {
    final Map<String, String> word = new HashMap<>();
    word.put(badWord, replacement);
    final List<Map<String, String>> allWords = getBadwords();
    allWords.add(word);
    config.set("badwords", allWords);
    saveConfig();
  }

  public void changeMessage(String newMessage) {
    config.set("badwords_message", newMessage);
    saveConfig();
  }

  public void changeCooldownTime(int cooldownTime) {
    config.set("cooldown_time", cooldownTime);
    saveConfig();
  }

  public int getCooldownTime() {
    return config.getInt("cooldown_time");
  }

  public boolean toggleChatCooldown() {
    if (isChatCooldownEnabled()) {
      config.set("chat_cooldown", false);
      saveConfig();
      return false;
    } else {
      config.set("chat_cooldown", true);
      saveConfig();
      return true;
    }
  }

  public boolean isChatCooldownEnabled() {
    return config.getBoolean("chat_cooldown");
  }

  public boolean togglePlugin() {
    if (config.getBoolean("enabled")) {
      config.set("enabled", false);
      saveConfig();
      return false;
    } else {
      config.set("enabled", true);
      saveConfig();
      return true;
    }
  }

  public boolean toggleReplacements() {
    if (config.getBoolean("replace_words")) {
      config.set("replace_words", false);
      saveConfig();
      return false;
    } else {
      config.set("replace_words", true);
      saveConfig();
      return true;
    }
  }
}
