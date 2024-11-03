package cc.atenea.dedsafioUtils.utilities;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlDatabase {
  private final File file;
  private final Yaml yaml;
  private final DedsafioPlugin plugin;
  private Map<String, Object> data;

  public YamlDatabase(DedsafioPlugin plugin, String filePath, Map<String, Object> defaultValues) {
    this.plugin = plugin;
    this.file = new File(plugin.getDataFolder(), filePath);
    this.yaml = new Yaml(setupYamlOptions());

    if (file.exists()) {
      load();
    } else {
      this.data = new HashMap<>(defaultValues);
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

  public Map<String, Object> getAll() {
    return data;
  }

  public List<String> getKeys() {
    return data.keySet().stream().toList();
  }

  public void delete(String key) {
    data.remove(key);
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
