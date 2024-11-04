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

  /**
   * Constructor for FileConfig.
   * Initializes and loads the configuration file. Creates the file if it does not exist.
   *
   * @param plugin   The instance of the JavaPlugin.
   * @param fileName The name of the file to load or create.
   */
  public FileConfig(JavaPlugin plugin, String fileName) {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder(), fileName);

    if (!this.file.exists()) {
      var created = this.file.getParentFile().mkdirs();

      if (plugin.getResource(fileName) == null) {
        try {
          var created2 = this.file.createNewFile();
          if (!created || !created2) {
            throw new IOException("Failed to create new file " + fileName);
          }
        } catch (IOException e) {
          plugin.getLogger().severe("Failed to create new file " + fileName);
        }
      } else {
        plugin.saveResource(fileName, false);
      }
    }

    this.configuration = YamlConfiguration.loadConfiguration(this.file);
  }

  /**
   * Retrieves a double value from the configuration file.
   *
   * @param path The path of the value in the configuration file.
   * @return The double value at the specified path, or 0 if not found.
   */
  public double getDouble(String path) {
    return configuration.contains(path) ? configuration.getDouble(path) : 0;
  }

  /**
   * Retrieves an integer value from the configuration file.
   *
   * @param path The path of the value in the configuration file.
   * @return The integer value at the specified path, or 0 if not found.
   */
  public int getInt(String path) {
    return configuration.contains(path) ? configuration.getInt(path) : 0;
  }

  /**
   * Retrieves a boolean value from the configuration file.
   *
   * @param path The path of the value in the configuration file.
   * @return The boolean value at the specified path, or false if not found.
   */
  public boolean getBoolean(String path) {
    return configuration.contains(path) && configuration.getBoolean(path);
  }

  /**
   * Retrieves a long value from the configuration file.
   *
   * @param path The path of the value in the configuration file.
   * @return The long value at the specified path, or 0L if not found.
   */
  public long getLong(String path) {
    return configuration.contains(path) ? configuration.getLong(path) : 0L;
  }

  /**
   * Retrieves a string value from the configuration file and translates color codes.
   *
   * @param path The path of the value in the configuration file.
   * @return The string value at the specified path with color codes translated, or null if not found.
   */
  public String getString(String path) {
    return configuration.contains(path)
      ? ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configuration.getString(path)))
      : null;
  }

  /**
   * Retrieves a string value from the configuration file with an optional fallback and color translation.
   *
   * @param path     The path of the value in the configuration file.
   * @param callback The fallback value if the path does not exist.
   * @param colorize Whether to translate color codes in the string.
   * @return The string value at the specified path, the callback if not found, optionally with color codes translated.
   */
  public String getString(String path, String callback, boolean colorize) {
    if (configuration.contains(path)) {
      String result = configuration.getString(path);
      if (result == null) {
        return callback;
      }

      return colorize
        ? ChatColor.translateAlternateColorCodes('&', result)
        : result;
    }
    return callback;
  }

  /**
   * Retrieves a list of strings from the configuration file and translates color codes.
   *
   * @param path The path of the list in the configuration file.
   * @return The list of strings at the specified path with color codes translated, or null if not found.
   */
  public List<String> getStringList(String path) {
    if (configuration.contains(path)) {
      List<String> strings = new ArrayList<>();
      for (String string : configuration.getStringList(path)) {
        strings.add(ChatColor.translateAlternateColorCodes('&', string));
      }

      return strings;
    }

    return null;
  }

  /**
   * Saves the current state of the configuration to the file.
   */
  public void save() {
    try {
      this.configuration.save(this.file);
    } catch (IOException e) {
      plugin.getLogger().severe("Failed to save file " + file.getName());
    }
  }

  /**
   * Reloads the configuration from the file, updating any changes made directly to the file.
   */
  public void reload() {
    this.configuration = YamlConfiguration.loadConfiguration(file);
  }

  /**
   * Sets a value in the configuration without saving immediately.
   *
   * @param path  The path to set in the configuration file.
   * @param value The value to set.
   */
  public void set(String path, Object value) {
    configuration.set(path, value);
  }

  /**
   * Sets a value in the configuration and saves it immediately to the file.
   *
   * @param path  The path to set in the configuration file.
   * @param value The value to set.
   */
  public void setAndSave(String path, Object value) {
    set(path, value);
    save();
  }

  /**
   * Sets a value in the configuration, saves it, and reloads the configuration file.
   *
   * @param path  The path to set in the configuration file.
   * @param value The value to set.
   */
  public void setAndReload(String path, Object value) {
    setAndSave(path, value);
    reload();
  }
}
