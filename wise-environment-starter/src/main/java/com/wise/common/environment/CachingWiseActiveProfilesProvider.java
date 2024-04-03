package com.wise.common.environment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingWiseActiveProfilesProvider implements WiseActiveProfilesProvider {

  private final List<WiseProfile> activeProfiles;

  private Map<WiseProfile, Boolean> isActiveCache = new ConcurrentHashMap<>();

  public CachingWiseActiveProfilesProvider(List<WiseProfile> activeProfiles) {
    this.activeProfiles = activeProfiles;
  }

  @Override
  public List<WiseProfile> getActiveProfiles() {
    return activeProfiles;
  }

  @Override
  public boolean isProfileActive(WiseProfile profile) {
    return isActiveCache.computeIfAbsent(profile, k -> {
      var activeProfiles = getActiveProfiles();

      for (var activeProfile : activeProfiles) {
        while (activeProfile != null) {
          if (activeProfile == profile) {
            return true;
          }
          activeProfile = activeProfile.parent();
        }
      }

      return false;
    });
  }
}
