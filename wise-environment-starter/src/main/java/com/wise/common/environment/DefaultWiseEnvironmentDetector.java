package com.wise.common.environment;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DefaultWiseEnvironmentDetector implements WiseEnvironmentDetector {

  private final String value;

  public DefaultWiseEnvironmentDetector(String value) {
    this.value = value;
  }

  @Override
  public List<WiseProfile> detectActiveProfiles() {
    final var result = new ArrayList<WiseProfile>();
    final var envs = StringUtils.split(value, ",");
    if (envs != null) {
      for (var env : envs) {
        final var trimmedEnv = StringUtils.trim(env);
        result.add(WiseProfile.getByName(trimmedEnv));
      }
    }

    if (result.isEmpty()) {
      if (isExecutedByIntegrationTest()) {
        result.add(WiseProfile.INTEGRATION_TEST);
      } else if (isExecutedByTest()) {
        result.add(WiseProfile.UNIT_TEST);
      }
    }

    return result;
  }

  protected boolean isExecutedByIntegrationTest() {
    for (var stackTraceElement : Thread.currentThread().getStackTrace()) {
      if ("org.springframework.boot.test.context.SpringBootContextLoader".equals(stackTraceElement.getClassName())) {
        return true;
      }
    }
    return false;
  }

  protected boolean isExecutedByTest() {
    for (var stackTraceElement : Thread.currentThread().getStackTrace()) {
      if ("org.junit.platform.launcher.core.DefaultLauncher".equals(stackTraceElement.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
