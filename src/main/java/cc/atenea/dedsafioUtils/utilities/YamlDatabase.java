package cc.atenea.dedsafioUtils.utilities;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlDatabase {
  private final File file;
  private final Yaml yaml;
  private final DedsafioPlugin plugin;
  private Map<String, Object> data;

  public YamlDatabase(DedsafioPlugin plugin) {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder(), "database.yml");
    this.yaml = new Yaml(setupYamlOptions());

    if (file.exists()) {
      load();
    } else {
      this.data = new HashMap<>(Map.of(
        "fogata-pos-one", "0,0,0",
        "fogata-pos-two", "0,0,0")
      );
      save();
    }
  }

  private DumperOptions setupYamlOptions() {
    DumperOptions options = new DumperOptions();
    options.setIndent(2);
    options.setPrettyFlow(true);
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    return options;
  }

  public void set(String key, Object value) {
    data.put(key, value);
  }

  public Object get(String key) {
    return data.getOrDefault(key, null);
  }

  public boolean getBoolean(String key) {
    return (boolean) data.getOrDefault(key, false);
  }

  public Map<String, Object> getAll() {
    return data;
  }

  public List<String> getKeys() {
    return data.keySet().stream().toList();
  }

  public void delete(String key) {
    data.remove(key);
  }

  public void setAndSave(String key, Object value) {
    set(key, value);
    save();
  }

  public void save() {
    try (Writer writer = new FileWriter(file)) {
      yaml.dump(data, writer);
    } catch (IOException e) {
      plugin.getLogger().severe("Error saving database: " + e.getMessage());
    }
  }

  private void load() {
    try (Reader reader = new FileReader(file)) {
      this.data = yaml.load(reader);
      if (data == null) {
        data = new HashMap<>();
      }
    } catch (IOException e) {
      plugin.getLogger().severe("Error loading database: " + e.getMessage());
    }
  }
}
