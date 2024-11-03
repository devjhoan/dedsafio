package cc.atenea.dedsafioUtils.resources.types;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.utilities.file.FileConfig;

import java.util.ArrayList;
import java.util.List;

public class AnimationResource {
  private final DedsafioPlugin plugin;

  public AnimationResource(DedsafioPlugin dedsafioPlugin) {
    super();

    this.plugin = dedsafioPlugin;
  }

  public List<String> getAnimation(String animationId) {
    List<List<Character>> animationFrames = new ArrayList<>();
    FileConfig animationFile = plugin.getFile("animations");

    int start = animationFile.getInt(animationId + ".start");
    int end = animationFile.getInt(animationId + ".end");

    for (int i = start; i <= end; i++) {
      List<Character> frame = new ArrayList<>();
      frame.add((char) i);
      animationFrames.add(frame);
    }

    List<String> animationStrings = new ArrayList<>();
    for (List<Character> frame : animationFrames) {
      animationStrings.add(frameToString(frame));
    }

    return animationStrings;
  }

  private String frameToString(List<Character> frame) {
    StringBuilder sb = new StringBuilder();
    for (Character ch : frame) {
      sb.append(ch);
    }

    return sb.toString();
  }
}
