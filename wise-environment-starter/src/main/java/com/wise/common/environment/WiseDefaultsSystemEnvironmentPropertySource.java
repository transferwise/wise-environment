package com.wise.common.environment;

import java.util.Map;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class WiseDefaultsSystemEnvironmentPropertySource extends MapPropertySource {

  public WiseDefaultsSystemEnvironmentPropertySource(String name, Map<String, Object> source) {
    super(name, source);
  }

  @Override
  public boolean containsProperty(String name) {
    return (getProperty(name) != null);
  }

  @Override
  @Nullable
  public Object getProperty(String name) {
    String actualName = resolvePropertyName(name);
    if (logger.isDebugEnabled() && !name.equals(actualName)) {
      logger.debug("PropertySource '" + getName() + "' does not contain property '" + name +
          "', but found equivalent '" + actualName + "'");
    }
    return super.getProperty(actualName);
  }

  /**
   * Check to see if this property source contains a property with the given name, or any underscore / uppercase variation thereof. Return the
   * resolved name if one is found or otherwise the original name. Never returns {@code null}.
   */
  protected final String resolvePropertyName(String name) {
    var nameWithDefaultPrefix = "WISE_DEFAULTS_" + name;

    Assert.notNull(name, "Property name must not be null");
    String resolvedName = checkPropertyName(nameWithDefaultPrefix);
    if (resolvedName != null) {
      return resolvedName;
    }
    String uppercasedName = nameWithDefaultPrefix.toUpperCase();
    if (!name.equals(uppercasedName)) {
      resolvedName = checkPropertyName(uppercasedName);
      if (resolvedName != null) {
        return resolvedName;
      }
    }
    return name;
  }

  @Nullable
  private String checkPropertyName(String name) {
    // Check name as-is
    if (containsKey(name)) {
      return name;
    }
    // Check name with just dots replaced
    String noDotName = name.replace('.', '_');
    if (!name.equals(noDotName) && containsKey(noDotName)) {
      return noDotName;
    }
    // Check name with just hyphens replaced
    String noHyphenName = name.replace('-', '_');
    if (!name.equals(noHyphenName) && containsKey(noHyphenName)) {
      return noHyphenName;
    }
    // Check name with dots and hyphens replaced
    String noDotNoHyphenName = noDotName.replace('-', '_');
    if (!noDotName.equals(noDotNoHyphenName) && containsKey(noDotNoHyphenName)) {
      return noDotNoHyphenName;
    }
    // Give up
    return null;
  }

  private boolean containsKey(String name) {
    return this.source.containsKey(name);
  }

}
