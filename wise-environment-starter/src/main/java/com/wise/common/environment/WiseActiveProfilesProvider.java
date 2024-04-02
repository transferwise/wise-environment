package com.wise.common.environment;

import java.util.List;

public interface WiseActiveProfilesProvider {

  List<WiseProfile> getActiveProfiles();

  boolean isProfileActive(WiseProfile profile);
}
