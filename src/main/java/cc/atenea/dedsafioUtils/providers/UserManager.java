package cc.atenea.dedsafioUtils.providers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {
  private final HashMap<UUID, User> users = new HashMap<>();

  public User getUser(Player player) {
    return getUser(player.getUniqueId());
  }

  public User getUser(UUID uuid) {
    if (!users.containsKey(uuid)) {
      users.put(uuid, new User(uuid));
    }

    return users.get(uuid);
  }
}
