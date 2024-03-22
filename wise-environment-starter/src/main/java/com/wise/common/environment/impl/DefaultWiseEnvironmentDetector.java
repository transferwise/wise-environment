package com.wise.common.environment.impl;

import com.wise.common.environment.WiseEnvironment;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DefaultWiseEnvironmentDetector implements WiseEnvironmentDetector {

  private final String value;

  public DefaultWiseEnvironmentDetector(String value) {
    this.value = value;
  }

  @Override
  public List<WiseEnvironment> detect() {
    final var result = new ArrayList<WiseEnvironment>();
    final var envs = StringUtils.split(value, ",");
    if (envs != null) {
      for (var env : envs) {
        final var trimmedEnv = StringUtils.trim(env);
        result.add(WiseEnvironment.getByName(trimmedEnv));
      }
    }

    if (result.isEmpty()) {
      if (isExecutedByIntegrationTest()) {
        result.add(WiseEnvironment.INTEGRATION_TEST);
      } else if (isExecutedByTest()) {
        result.add(WiseEnvironment.UNIT_TEST);
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
