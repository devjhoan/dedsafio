package cc.atenea.dedsafioUtils.utilities.file;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class FileConfig {

  private final JavaPlugin plugin;
  private final File file;
  private FileConfiguration configuration;

  public FileConfig(JavaPlugin plugin, String fileName) {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder(), fileName);

    if (!this.file.exists()) {
      this.file.getParentFile().mkdirs();

      if (plugin.getResource(fileName) == null) {
        try {
          this.file.createNewFile();
        }
        catch (IOException e) {
          plugin.getLogger().severe("Failed to create new file " + fileName);
        }
      } else {
        plugin.saveResource(fileName, false);
      }
    }

    this.configuration = YamlConfiguration.loadConfiguration(this.file);
  }

  public double getDouble(String path) {
    if (configuration.contains(path)) {
      return configuration.getDouble(path);
    }
    return 0;
  }

  public int getInt(String path) {
    if (configuration.contains(path)) {
      return configuration.getInt(path);
    }
    return 0;
  }

  public boolean getBoolean(String path) {
    if (configuration.contains(path)) {
      return configuration.getBoolean(path);
    }
    return false;
  }

  public long getLong(String path) {
    if (configuration.contains(path)) {
      return configuration.getLong(path);
    }
    return 0L;
  }

  public String getString(String path) {
    if (configuration.contains(path)) {
      return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configuration.getString(path)));
    }
    return null;
  }

  public String getString(String path, String callback, boolean colorize) {
    if (configuration.contains(path)) {
      if (colorize) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configuration.getString(path)));
      } else {
        return configuration.getString(path);
      }
    }
    return callback;
  }

  public List<String> getStringList(String path) {
    if (configuration.contains(path)) {
      ArrayList<String> strings = new ArrayList<>();
      for (String string : configuration.getStringList(path)) {
        strings.add(ChatColor.translateAlternateColorCodes('&', string));
      }
      return strings;
    }
    return null;
  }

  public void save() {
    try {
      this.configuration.save(this.file);
    }
    catch (IOException e) {
      plugin.getLogger().severe("Failed to save file " + file.getName());
    }
  }

  public void reload() {
    this.configuration = YamlConfiguration.loadConfiguration(file);
  }
}