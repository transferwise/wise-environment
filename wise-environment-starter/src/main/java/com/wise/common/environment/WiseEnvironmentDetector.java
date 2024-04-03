package com.wise.common.environment;

import java.util.List;

public interface WiseEnvironmentDetector {

  List<WiseProfile> detectActiveProfiles();
}
