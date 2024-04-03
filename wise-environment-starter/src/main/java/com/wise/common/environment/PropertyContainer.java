package com.wise.common.environment;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.origin.Origin;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)
public class PropertyContainer {

  private Object value;

  private WiseEnvironmentPropertyOrigin origin;

  public PropertyContainer(String source, WiseProfile profile, Object value) {
    Assert.notNull(value, "Value must not be null");
    this.origin = new WiseEnvironmentPropertyOrigin(source, profile);
    this.value = value;
  }

  @Data
  public static class WiseEnvironmentPropertyOrigin implements Origin {

    private String source;

    private WiseProfile profile;

    public WiseEnvironmentPropertyOrigin(String source, WiseProfile profile) {
      Assert.notNull(source, "Source must not be null");
      Assert.notNull(profile, "Profile must not be null");
      this.source = source;
      this.profile = profile;
    }

    public String toString() {
      return "Wise profile: '" + profile + "', source: '" + source + "'";
    }
  }
}
