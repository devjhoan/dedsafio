package cc.atenea.dedsafioUtils.resources;

import cc.atenea.dedsafioUtils.DedsafioPlugin;
import cc.atenea.dedsafioUtils.resources.types.ConfigResource;
import cc.atenea.dedsafioUtils.resources.types.LanguageResource;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResourceManager {

  private final DedsafioPlugin plugin;
  private final List<Resource> resources;

  public ResourceManager(DedsafioPlugin plugin) {
    this.plugin = plugin;

    this.resources = new ArrayList<>();
    this.resources.add(new LanguageResource());
    this.resources.add(new ConfigResource());

    this.onRefresh();
  }

  public void onRefresh() {
    resources.forEach(resource -> resource.initialize(plugin));
  }
}
