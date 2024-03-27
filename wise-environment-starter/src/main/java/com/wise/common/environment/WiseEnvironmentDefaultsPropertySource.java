package com.wise.common.environment;

import com.wise.common.environment.WiseEnvironmentDefaultsPropertySource.WiseEnvironmentDefaultsPropertySourceSource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.EnumerablePropertySource;

public class WiseEnvironmentDefaultsPropertySource extends EnumerablePropertySource<WiseEnvironmentDefaultsPropertySourceSource> implements
    OriginLookup<String> {

  public WiseEnvironmentDefaultsPropertySource() {
    super("WiseEnvironmentAwareDefaultsPropertySource", new WiseEnvironmentDefaultsPropertySourceSource());
  }

  @Override
  public Object getProperty(String name) {
    return WiseEnvironment.getDefaultProperty(name);
  }

  @Override
  public String[] getPropertyNames() {
    return WiseEnvironment.getDefaultPropertyNames();
  }

  @Override
  public Origin getOrigin(String name) {
    var container = WiseEnvironment.getDefaultPropertyContainer(name);
    return container == null ? null : container.getOrigin();
  }

  static class WiseEnvironmentDefaultsPropertySourceSource implements OriginLookup<String> {

    @Override
    public Origin getOrigin(String name) {
      var container = WiseEnvironment.getDefaultPropertyContainer(name);
      return container == null ? null : container.getOrigin();
    }
  }
}
