package cc.atenea.dedsafioUtils.resources.types;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.Resource;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;

public class ConfigResource extends Resource {
  public static boolean WelcomeMessageEnabled;
  public static String WelcomeMessage;

  public static boolean LeaveMessageEnabled;
  public static String LeaveMessage;

  public static boolean TotemUseMessageEnabled;
  public static String TotemUseMessage;

  public static boolean TimeControllerEnabled;
  public static String TimeControllerBossbarTitle;
  public static String TimeControllerOpenServer;
  public static String TimeControllerCloseServer;
  public static int TimeControllerBossbarIn;

  public static boolean TimeControllerKickEnabled;
  public static String TimeControllerKickMessage;
  public static String TimeControllerBypassPermission;

  public static boolean DeathSystemEnabled;
  public static String DeathSystemBypassPermission;
  public static String DeathSystemMessage;
  public static boolean DeathSystemImmediateRespawn;

  public static boolean DeathSystemAnimationEnabled;
  public static String DeathSystemAnimationPosition;
  public static String DeathSystemAnimationShowTo;
  public static String DeathSystemAnimationSound;

  public static boolean DeathSystemBanEnabled;
  public static String DeathSystemBanMessage;
  public static int DeathSystemBanAfterSeconds;

  public static boolean ChangesEnabled;
  public static String ChangesBypassPermission;

  public static boolean NetherDisabled;
  public static boolean AlwaysSpawnElectricCreepers;
  public static boolean ButtonInstantKill;
  public static boolean VillagersCannotBreed;
  public static boolean SpiderWebsOnHit;
  public static boolean BreakWebsSpawnPoisonousSpiders;
  public static boolean EnderpearlHalfHealth;
  public static boolean UsingDoorsCausesInstantKill;
  public static boolean PiglinsDropGoldNuggets;
  public static boolean IronGolemsReplacedByWardens;

  public static String AnnounceBlue;
  public static String AnnounceColor;
  public static String AnnounceCyan;
  public static String AnnounceGreen;
  public static String AnnounceOff;
  public static String AnnounceOrange;
  public static String AnnouncePink;
  public static String AnnouncePurple;
  public static String AnnounceRed;
  public static String AnnounceYellow;
  public static String ReviveMenu;
  public static String WhiteScreen;

  @Override
  public void initialize(DedsafioPlugin dedsafio) {
    FileConfig languageFile = dedsafio.getFile("config");

    WelcomeMessage = languageFile.getString("on-join.message");
    WelcomeMessageEnabled = languageFile.getBoolean("on-join.enabled");

    LeaveMessage = languageFile.getString("on-leave.message");
    LeaveMessageEnabled = languageFile.getBoolean("on-leave.enabled");

    TotemUseMessage = languageFile.getString("on-totem-use.message");
    TotemUseMessageEnabled = languageFile.getBoolean("on-totem-use.enabled");

    TimeControllerEnabled = languageFile.getBoolean("time-controller.enabled");
    TimeControllerBossbarTitle = languageFile.getString("time-controller.title");
    TimeControllerOpenServer = languageFile.getString("time-controller.open-server");
    TimeControllerCloseServer = languageFile.getString("time-controller.close-server");
    TimeControllerBossbarIn = languageFile.getInt("time-controller.bossbar-in");

    TimeControllerKickEnabled = languageFile.getBoolean("time-controller.kick.enabled");
    TimeControllerKickMessage = languageFile.getString("time-controller.kick.message");
    TimeControllerBypassPermission = languageFile.getString("time-controller.kick.bypass-permission");

    DeathSystemEnabled = languageFile.getBoolean("death-system.enabled");
    DeathSystemBypassPermission = languageFile.getString("death-system.bypass-permission");
    DeathSystemMessage = languageFile.getString("death-system.message");
    DeathSystemImmediateRespawn = languageFile.getBoolean("death-system.immediate-respawn");

    DeathSystemBanEnabled = languageFile.getBoolean("death-system.ban-player.enabled");
    DeathSystemBanMessage = languageFile.getString("death-system.ban-player.message");
    DeathSystemBanAfterSeconds = languageFile.getInt("death-system.ban-player.after-seconds");

    DeathSystemAnimationEnabled = languageFile.getBoolean("death-system.animation.enabled");
    DeathSystemAnimationPosition = languageFile.getString("death-system.animation.position");
    DeathSystemAnimationShowTo = languageFile.getString("death-system.animation.show-to");
    DeathSystemAnimationSound = languageFile.getString("death-system.animation.sound");

    ChangesEnabled = languageFile.getBoolean("changes-settings.enabled");
    ChangesBypassPermission = languageFile.getString("changes-settings.bypass-permission");

    AnnounceBlue = languageFile.getString("unicodes.announce_blue");
    AnnounceColor = languageFile.getString("unicodes.announce_color");
    AnnounceCyan = languageFile.getString("unicodes.announce_cyan");
    AnnounceGreen = languageFile.getString("unicodes.announce_green");
    AnnounceOff = languageFile.getString("unicodes.announce_off");
    AnnounceOrange = languageFile.getString("unicodes.announce_orange");
    AnnouncePink = languageFile.getString("unicodes.announce_pink");
    AnnouncePurple = languageFile.getString("unicodes.announce_purple");
    AnnounceRed = languageFile.getString("unicodes.announce_red");
    AnnounceYellow = languageFile.getString("unicodes.announce_yellow");
    WhiteScreen = languageFile.getString("unicodes.white-screen");
    ReviveMenu = languageFile.getString("unicodes.revive-menu");

    NetherDisabled = languageFile.getBoolean("changes-settings.changes.disable-nether");
    AlwaysSpawnElectricCreepers = languageFile.getBoolean("changes-settings.changes.always-spawn-electric-creepers");
    ButtonInstantKill = languageFile.getBoolean("changes-settings.changes.button-instant-kill");
    VillagersCannotBreed = languageFile.getBoolean("changes-settings.changes.villagers-cannot-breed");
    SpiderWebsOnHit = languageFile.getBoolean("changes-settings.changes.spider-webs-on-hit");
    BreakWebsSpawnPoisonousSpiders = languageFile.getBoolean("changes-settings.changes.break-webs-spawn-poisonous-spiders");
    EnderpearlHalfHealth = languageFile.getBoolean("changes-settings.changes.enderpearl-half-health");
    UsingDoorsCausesInstantKill = languageFile.getBoolean("changes-settings.changes.using-doors-causes-instant-kill");
    PiglinsDropGoldNuggets = languageFile.getBoolean("changes-settings.changes.piglins-drop-gold-nuggets");
    IronGolemsReplacedByWardens = languageFile.getBoolean("changes-settings.changes.iron-golems-replaced-by-wardens");
  }
}
