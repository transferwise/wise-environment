package com.wise.common.environment;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.EnumerablePropertySource;

public class WiseEnvironmentDefaultsPropertySource extends EnumerablePropertySource<String> implements OriginLookup<String> {

  public WiseEnvironmentDefaultsPropertySource() {
    super("WiseEnvironmentAwareDefaultsPropertySource", "WiseEnvironmentAwareDefaultsPropertySource");
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
    var container = WiseEnvironment.getDefaultPropertyContainer(this.name);
    return container == null ? null : container.getOrigin();
  }
}
