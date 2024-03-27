package com.wise.common.environment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingWiseEnvironmentProvider implements WiseEnvironmentProvider {

  private final List<WiseEnvironment> activeEnvironments;

  private Map<WiseEnvironment, Boolean> isActiveCache = new ConcurrentHashMap<>();

  public CachingWiseEnvironmentProvider(List<WiseEnvironment> activeEnvironments) {
    this.activeEnvironments = activeEnvironments;
  }

  @Override
  public List<WiseEnvironment> getActiveEnvironments() {
    return activeEnvironments;
  }

  @Override
  public boolean isEnvironmentActive(WiseEnvironment environment) {
    return isActiveCache.computeIfAbsent(environment, k -> {
      var activeEnvironments = getActiveEnvironments();

      for (var activeEnvironment : activeEnvironments) {
        while (activeEnvironment != null) {
          if (activeEnvironment == environment) {
            return true;
          }
          activeEnvironment = activeEnvironment.parent();
        }
      }

      return false;
    });
  }
}
