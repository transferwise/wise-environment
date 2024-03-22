package com.wise.common.environment.impl;

import com.wise.common.environment.WiseEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

public class WiseEnvironmentDefaultsPropertySource extends EnumerablePropertySource<String> {

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
}
