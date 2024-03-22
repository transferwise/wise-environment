package com.wise.common.environment;

import java.util.Map;
import java.util.Properties;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.Nullable;

public class WiseDefaultsPropertiesPropertySource extends MapPropertySource {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public WiseDefaultsPropertiesPropertySource(String name, Properties source) {
    super(name, (Map) source);
  }

  protected WiseDefaultsPropertiesPropertySource(String name, Map<String, Object> source) {
    super(name, source);
  }

  @Override
  @Nullable
  public Object getProperty(String name) {
    return this.source.get("wise.defaults." + name);
  }

}
