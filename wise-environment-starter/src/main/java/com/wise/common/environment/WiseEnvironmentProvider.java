package com.wise.common.environment;

import java.util.List;

public interface WiseEnvironmentProvider {

  List<WiseEnvironment> getActiveEnvironments();

  boolean isEnvironmentActive(WiseEnvironment environment);
}
