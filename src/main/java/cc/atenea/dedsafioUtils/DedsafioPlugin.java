package cc.atenea.dedsafioUtils;

import cc.atenea.dedsafioUtils.animations.AnimationsManager;
import cc.atenea.dedsafioUtils.commands.*;
import cc.atenea.dedsafioUtils.events.*;
import cc.atenea.dedsafioUtils.events.changes.*;
import cc.atenea.dedsafioUtils.features.BossBarManager;
import cc.atenea.dedsafioUtils.features.TimeController;
import cc.atenea.dedsafioUtils.items.*;
import cc.atenea.dedsafioUtils.items.core.ItemManager;
import cc.atenea.dedsafioUtils.providers.UserManager;
import cc.atenea.dedsafioUtils.resources.ResourceManager;
import cc.atenea.dedsafioUtils.utilities.ChatUtil;
import cc.atenea.dedsafioUtils.utilities.MagicGUI;
import cc.atenea.dedsafioUtils.utilities.YamlDatabase;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class DedsafioPlugin extends JavaPlugin {
  private final Map<String, FileConfig> files = new HashMap<>();
  public BossBarManager bossBarManager = new BossBarManager();
  public YamlDatabase db = new YamlDatabase(this);
  public AnimationsManager animations;
  public UserManager userManager;
  private ResourceManager resourceManager;
  private TimeController timeController;

  public static DedsafioPlugin getInstance() {
    return getPlugin(DedsafioPlugin.class);
  }

  @Override
  public void onLoad() {
    CommandAPI.onLoad(
      new CommandAPIBukkitConfig(this)
        .silentLogs(true)
        .setNamespace("dedsafio")
    );
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    getLogger().info(ChatUtil.translate("&c&lDedsafio-Utils enabled"));

    // Register Files
    files.put("language", new FileConfig(this, "language.yml"));
    files.put("animations", new FileConfig(this, "animations.yml"));
    files.put("config", new FileConfig(this, "config.yml"));

    // Register Managers
    this.resourceManager = new ResourceManager(this);
    this.timeController = new TimeController(this);
    this.animations = new AnimationsManager(this);
    this.userManager = new UserManager(this);
    MagicGUI.tryToLoadFor(this);

    // Register Command
    CommandAPI.onEnable();
    registerCommands();

    // Register Events
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new PlayerDeathEvent(this), this);
    pluginManager.registerEvents(new PlayerJoinEvent(), this);
    pluginManager.registerEvents(new EntityResurrectEvent(), this);
    pluginManager.registerEvents(new ItemEventListener(), this);
    pluginManager.registerEvents(new PlayerQuitEvent(), this);

    // Eventos de cambios
    pluginManager.registerEvents(new DisableNether(), this);
    pluginManager.registerEvents(new ElectricCreeperSpawn(), this);
    pluginManager.registerEvents(new VillagersCannotBreed(), this);
    pluginManager.registerEvents(new ButtonInstantKill(), this);
    pluginManager.registerEvents(new SpiderWebsOnHit(), this);
    pluginManager.registerEvents(new BreakWebsSpawnSpiders(), this);
    pluginManager.registerEvents(new EnderpearlHalfHealth(), this);
    pluginManager.registerEvents(new DoorsCausesInstantKill(), this);
    pluginManager.registerEvents(new PiglinsDropGoldNuggets(), this);
    pluginManager.registerEvents(new IronGolemsReplacedByWardens(), this);

    // Register Items
    ItemManager.registerItem(new EnderBagItem());
    ItemManager.registerItem(new InfernalSword());
    ItemManager.registerItem(new GhostSword());
    ItemManager.registerItem(new SunblockItem());
    ItemManager.registerItem(new PortableGoldenAnvilItem());
    ItemManager.registerItem(new BlueCapsuleItem());
    ItemManager.registerItem(new ForkItem());
    ItemManager.registerItem(new ResurrectionSpoonItem());
    ItemManager.registerItem(new SpoonItem());
    ItemManager.registerItem(new SpawnStickItem());
    ItemManager.registerItem(new MarkerItem(this));
  }

  @Override
  public void onDisable() {
    getLogger().info(ChatUtil.translate("&c&lDedsafio-Utils disabled"));
    timeController.stop();
    MagicGUI.tryToUnload();
  }

  public void onReload() {
    files.values().forEach(FileConfig::reload);
    resourceManager.onRefresh();
    animations.reload();
    userManager.reload();
  }

  public void registerCommands() {
    CommandAPI.registerCommand(RuletaCommand.class);
    CommandAPI.registerCommand(TpCommand.class);
    CommandAPI.registerCommand(DedsafioCommand.class);
    CommandAPI.registerCommand(TimerCommand.class);
    CommandAPI.registerCommand(ConfigCommand.class);
    CommandAPI.registerCommand(ItemsCommand.class);
    CommandAPI.registerCommand(FogataCommand.class);
    CommandAPI.registerCommand(SoulCommand.class);
    CommandAPI.registerCommand(ReviveCommand.class);
    CommandAPI.registerCommand(EonCommand.class);
  }

  public FileConfig getFile(String name) {
    return files.get(name);
  }
}
