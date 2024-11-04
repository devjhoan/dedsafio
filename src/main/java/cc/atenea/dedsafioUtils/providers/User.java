package cc.atenea.dedsafioUtils.providers;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
  private final DedsafioPlugin plugin = DedsafioPlugin.getInstance();
  private final File userFile;
  private final UUID uuid;
  private FileConfiguration userConfig;

  public User(UUID uuid) {
    File dataFolder = plugin.getDataFolder();

    this.uuid = uuid;
    this.userFile = new File(dataFolder + File.separator + "data", uuid + ".yml");

    if (!userFile.exists()) {
      registerUserFile();
    } else {
      this.userConfig = YamlConfiguration.loadConfiguration(userFile);
    }
  }

  public boolean hasSoul() {
    return this.userConfig.getBoolean("soul", false);
  }

  public List<String> soulUsedWith() {
    return this.userConfig.getStringList("soulUsedWith");
  }

  public void setSoul(boolean hasSoul) {
    this.userConfig.set("soul", hasSoul);
    save();
  }

  public void addSoulUsedWith(String name) {
    List<String> list = this.userConfig.getStringList("soulUsedWith");
    list.add(name);

    this.userConfig.set("soulUsedWith", list);
    save();
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
    userConfig.set("soul", true);
    userConfig.set("soulUsedWith", new ArrayList<>());

    save();
  }

  private void save() {
    try {
      userConfig.save(userFile);
    } catch (IOException e) {
      this.plugin.getLogger().severe("Failed to save user file: " + e.getMessage());
    }
  }
}
