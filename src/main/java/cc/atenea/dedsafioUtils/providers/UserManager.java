package cc.atenea.dedsafioUtils.providers;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {
  private final HashMap<UUID, User> users = new HashMap<>();
  private final DedsafioPlugin plugin;
  private final File dataFolder;

  public UserManager(DedsafioPlugin plugin) {
    this.plugin = plugin;
    this.dataFolder = new File(plugin.getDataFolder() + File.separator + "data");

    if (!dataFolder.exists()) {
      boolean created = dataFolder.mkdirs();
      if (!created) {
        throw new RuntimeException("Failed to create data folder");
      }
    }

    loadAllUsers();
  }

  public User getUser(Player player) {
    return getUser(player.getUniqueId());
  }

  public User getUser(UUID uuid) {
    if (!users.containsKey(uuid)) {
      users.put(uuid, new User(uuid));
    }

    return users.get(uuid);
  }

  public void loadAllUsers() {
    if (dataFolder.exists() && dataFolder.isDirectory()) {
      File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
      if (files == null) return;

      for (File file : files) {
        try {
          String uuidStr = file.getName().replace(".yml", "");
          UUID uuid = UUID.fromString(uuidStr);

          if (!users.containsKey(uuid)) {
            users.put(uuid, new User(uuid));
          }
        } catch (IllegalArgumentException e) {
          System.out.println(e);
          plugin.getLogger().severe("Archivo en data/ no es un UUID válido: " + file.getName());
        }
      }
    }
  }

  public void reload() {
    users.clear();
    loadAllUsers();
  }

  public HashMap<UUID, User> getAllUsers() {
    return users;
  }

  public List<User> getDeadUsers() {
    return users
      .values()
      .stream()
      .filter(User::isDead)
      .toList();
  }
}
