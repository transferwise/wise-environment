package com.wise.common.environment;

import com.wise.common.environment.WiseEnvironmentProperties.SubType;
import com.wise.common.environment.WiseEnvironmentProperties.Type;

public class DefaultWiseEnvironment implements WiseEnvironment {

  private final Type type;
  private final SubType subType;

  public DefaultWiseEnvironment(WiseEnvironmentProperties wiseEnvironmentProperties) {
    var type = wiseEnvironmentProperties.getType();
    if (type == null) {
      if (isExecutedByIntegrationTest()) {
        type = Type.INTEGRATION_TEST;
      }
    }
    if (type == null) {
      throw new IllegalStateException("Wise environment type is not specified. You can do that through 'wise.environment.type' property.");
    }

    this.type = type;
    this.subType = wiseEnvironmentProperties.getSubType();
  }

  @Override
  public boolean isProduction() {
    return Type.PRODUCTION == type;
  }

  @Override
  public boolean isStaging() {
    return Type.STAGING == type;
  }

  @Override
  public boolean isIntegrationTest() {
    return Type.INTEGRATION_TEST == type;
  }

  @Override
  public boolean isDevelopment() {
    return Type.DEVELOPMENT == type;
  }

  @Override
  public boolean isSandbox() {
    return SubType.SANDBOX == subType;
  }

  @Override
  public boolean isCustomEnvironment() {
    return SubType.CUSTOM_ENVS == subType;
  }

  protected boolean isExecutedByIntegrationTest() {
    for (var stackTraceElement : Thread.currentThread().getStackTrace()) {
      if ("org.springframework.boot.test.context.SpringBootContextLoader".equals(stackTraceElement.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
