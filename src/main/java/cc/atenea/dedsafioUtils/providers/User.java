package cc.atenea.dedsafioUtils.providers;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class User {
  private final DedsafioPlugin plugin = DedsafioPlugin.getInstance();
  private FileConfiguration userConfig;
  private final File userFile;
  private final UUID uuid;

  public User(UUID uuid) {
    File dataFolder = plugin.getDataFolder();

    this.userFile = new File(dataFolder + File.separator + "data", uuid + ".yml");
    this.uuid = uuid;

    if (!userFile.exists()) {
      registerUserFile();
    } else {
      this.userConfig = YamlConfiguration.loadConfiguration(userFile);
    }

    validateUserProfile();
  }

  public boolean hasSoul() {
    return this.userConfig.getBoolean("soul", false);
  }

  public void setSoul(boolean hasSoul) {
    this.userConfig.set("soul", hasSoul);
    save();
  }

  public boolean isDead() {
    return this.userConfig.getBoolean("dead", false);
  }

  public void addRevivedTimes() {
    this.userConfig.set("revived-times", getRevivedTimes() + 1);
    save();
  }

  public int getRevivedTimes() {
    return this.userConfig.getInt("revived-times", 0);
  }

  public void setDead(boolean dead) {
    this.userConfig.set("dead", dead);
    save();
  }

  public void setAlertRevive(boolean alertRevive) {
    this.userConfig.set("alert-revive", alertRevive);
    save();
  }

  public boolean getAlertRevive() {
    return this.userConfig.getBoolean("alert-revive", false);
  }

  private void registerUserFile() {
    try {
      boolean created = userFile.getParentFile().mkdirs();
      boolean created2 = userFile.createNewFile();

      if (!created || !created2) {
        this.plugin.getLogger().severe("Failed to create user file: " + userFile.getAbsolutePath());
      }
    } catch (IOException e) {
      this.plugin.getLogger().severe("Failed to create user file: " + e.getMessage());
    }

    userConfig = YamlConfiguration.loadConfiguration(userFile);
  }

  public void validateUserProfile() {
    if (!userConfig.isList("soulUsedWith")) {
      userConfig.set("soulUsedWith", new ArrayList<>());
    }

    if (!userConfig.isBoolean("soul")) {
      userConfig.set("soul", true);
    }

    if (!userConfig.isBoolean("dead")) {
      userConfig.set("dead", false);
    }

    if (!userConfig.isInt("revived-times")) {
      userConfig.set("revived-times", 0);
    }

    if (!userConfig.isBoolean("alert-revive")) {
      userConfig.set("alert-revive", false);
    }

    save();
  }

  private void save() {
    try {
      userConfig.save(userFile);
    } catch (IOException e) {
      this.plugin.getLogger().severe("Failed to save user file: " + e.getMessage());
    }
  }

  public UUID getUuid() {
    plugin.getLogger().info("UUID: " + uuid);
    return uuid;
  }
}
