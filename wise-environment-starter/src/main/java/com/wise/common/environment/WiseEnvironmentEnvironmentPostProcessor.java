package com.wise.common.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class WiseEnvironmentEnvironmentPostProcessor implements EnvironmentPostProcessor {

  private static final String SYSTEM_ENV_PROPERTY_SOURCE_KEY = WiseEnvironmentEnvironmentPostProcessor.class.getName() + ".systemEnv";
  private static final String SYSTEM_PROPS_PROPERTY_SOURCE_KEY = WiseEnvironmentEnvironmentPostProcessor.class.getName() + ".systemProps";

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    environment.getPropertySources().addLast(
        new WiseDefaultsPropertiesPropertySource(SYSTEM_PROPS_PROPERTY_SOURCE_KEY, environment.getSystemProperties()));
    environment.getPropertySources().addLast(
        new WiseDefaultsSystemEnvironmentPropertySource(SYSTEM_ENV_PROPERTY_SOURCE_KEY, environment.getSystemEnvironment()));
  }
}
