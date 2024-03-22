package com.wise.common.environment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WiseEnvironmentProperties {

  private Type type;

  private SubType subType;

  public enum Type {
    PRODUCTION,
    STAGING,
    INTEGRATION_TEST,
    DEVELOPMENT
  }

  public enum SubType {
    SANDBOX,
    CUSTOM_ENVS
  }
}
