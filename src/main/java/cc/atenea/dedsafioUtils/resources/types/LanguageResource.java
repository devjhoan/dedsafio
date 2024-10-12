package cc.atenea.dedsafioUtils.resources.types;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.Resource;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;

public class LanguageResource extends Resource {
    public static String[] DEDSAFIO_COMMAND_MESSAGE_HELP;
    public static String DEDSAFIO_COMMAND_MESSAGE_RELOAD;

    public static String[] ANIMATION_COMMAND_MESSAGE_HELP;

    @Override
    public void initialize(DedsafioPlugin dedsafio) {
        FileConfig languageFile = dedsafio.getFile("language");

        DEDSAFIO_COMMAND_MESSAGE_HELP = languageFile.getStringList("dedsafio-command-message.help").toArray(new String[0]);
        DEDSAFIO_COMMAND_MESSAGE_RELOAD = languageFile.getString("dedsafio-command-message.reload");
        ANIMATION_COMMAND_MESSAGE_HELP = languageFile.getStringList("animation-command-message.help").toArray(new String[0]);
    }
}
