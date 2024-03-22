package com.wise.common.environment;

public interface WiseEnvironment {
  boolean isProduction();

  boolean isStaging();

  boolean isIntegrationTest();

  boolean isDevelopment();

  boolean isSandbox();

  boolean isCustomEnvironment();
}
