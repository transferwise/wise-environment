package com.wise.common.environment;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.origin.Origin;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)
public class PropertyContainer {

  private Object value;

  private WiseEnvironmentOrigin origin;

  public PropertyContainer(String source, WiseEnvironment environment, Object value) {
    Assert.notNull(value, "Value must not be null");
    this.origin = new WiseEnvironmentOrigin(source, environment);
    this.value = value;
  }

  @Data
  public static class WiseEnvironmentOrigin implements Origin {

    private String source;

    private WiseEnvironment environment;

    public WiseEnvironmentOrigin(String source, WiseEnvironment environment) {
      Assert.notNull(source, "Source must not be null");
      Assert.notNull(environment, "Environment must not be null");
      this.source = source;
      this.environment = environment;
    }

    public String toString() {
      return "Wise environment: '" + environment + "', source: '" + source + "'";
    }
  }
}
