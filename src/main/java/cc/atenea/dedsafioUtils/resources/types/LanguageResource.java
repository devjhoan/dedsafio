package cc.atenea.dedsafioUtils.resources.types;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.Resource;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;

import java.util.Map;
import java.util.stream.Collectors;

public class LanguageResource extends Resource {
  public static String[] DEDSAFIO_COMMAND_MESSAGE_HELP;
  public static String DEDSAFIO_COMMAND_MESSAGE_RELOAD;
  public static String[] ANIMATION_COMMAND_MESSAGE_HELP;
  public static String[] ANIMATION_COMMAND_MESSAGE_INVALID_COLOR;
  public static String[] TP_COMMAND_MESSAGE_HELP;
  public static String TP_COMMAND_MESSAGE_SUCCESS;

  public static final Map<String, String> RULETA_COLORS = Map.ofEntries(
      Map.entry("yellow", "amarilla"),
      Map.entry("blue", "azul"),
      Map.entry("purple", "morada"),
      Map.entry("orange", "naranja"),
      Map.entry("red", "roja"),
      Map.entry("pink", "rosada"),
      Map.entry("cyan", "turqueza"),
      Map.entry("green", "verde")
  );

  public static Map<String, String[]> MESSAGE_RULETA_COLORS;

  @Override
  public void initialize(DedsafioPlugin dedsafio) {
    FileConfig languageFile = dedsafio.getFile("language");

    DEDSAFIO_COMMAND_MESSAGE_HELP = languageFile.getStringList("dedsafio-command-message.help").toArray(new String[0]);
    DEDSAFIO_COMMAND_MESSAGE_RELOAD = languageFile.getString("dedsafio-command-message.reload");
    ANIMATION_COMMAND_MESSAGE_HELP = languageFile.getStringList("animation-command-message.help").toArray(new String[0]);
    ANIMATION_COMMAND_MESSAGE_INVALID_COLOR = languageFile.getStringList("animation-command-message.invalid-color").toArray(new String[0]);
    TP_COMMAND_MESSAGE_HELP = languageFile.getStringList("tp-command-message.help").toArray(new String[0]);
    TP_COMMAND_MESSAGE_SUCCESS = languageFile.getString("tp-command-message.success");

    MESSAGE_RULETA_COLORS = RULETA_COLORS.keySet().stream().map(s -> {
      String[] colors = languageFile.getStringList("ruleta." + s).toArray(new String[0]);
      return Map.entry(s, colors);
    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
