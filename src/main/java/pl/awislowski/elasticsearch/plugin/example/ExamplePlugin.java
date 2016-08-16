package pl.awislowski.elasticsearch.plugin.example;

import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.inject.multibindings.Multibinder;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.action.cat.AbstractCatAction;

import java.util.Collection;
import java.util.Collections;

public class ExamplePlugin extends Plugin{

  @Override
  public String name() {
    return "example-plugin";
  }

  @Override
  public String description() {
    return "Example plugin desc";
  }

  @Override
  public Collection<Module> nodeModules() {
    return Collections.<Module>singletonList(new ConfiguredExampleModule());  }

  public static class ConfiguredExampleModule extends AbstractModule {
    @Override
    protected void configure() {
      Multibinder<AbstractCatAction> catActionMultibinder = Multibinder.newSetBinder(binder(), AbstractCatAction.class);
      catActionMultibinder.addBinding().to(ExampleCatAction.class).asEagerSingleton();
    }
  }
}
