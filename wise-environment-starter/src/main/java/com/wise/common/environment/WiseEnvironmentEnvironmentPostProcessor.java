package com.wise.common.environment;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@Order
public class WiseEnvironmentEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    var wiseEnvironmentDetector = new DefaultWiseEnvironmentDetector(environment.getProperty("wise.environments.active"));
    final var activeEnvironments = wiseEnvironmentDetector.detect();

    if (activeEnvironments.isEmpty()) {
      log.info("No active Wise environments detected.");
    } else {
      log.info("Active Wise environments detected are '{}'.", activeEnvironments.stream().map(Enum::name).collect(Collectors.joining(",")));
    }

    WiseEnvironment.init(activeEnvironments);

    environment.getPropertySources().addLast(new WiseEnvironmentDefaultsPropertySource());
  }
}
