package com.wise.common.environment;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@Order
public class WiseEnvironmentEnvironmentPostProcessor implements EnvironmentPostProcessor {

  private final Log log;

  public WiseEnvironmentEnvironmentPostProcessor(DeferredLogFactory deferredLogFactory) {
    log = deferredLogFactory.getLog(WiseEnvironmentEnvironmentPostProcessor.class);
  }

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication springApplication) {
    var propValue = environment.getProperty("wise.profiles.active");
    if (propValue == null) {
      // Deprecated property, can be removed later.
      propValue = environment.getProperty("wise.environments.active");
    }

    var wiseEnvironmentDetector = new DefaultWiseEnvironmentDetector(propValue);
    final var activeEnvironments = wiseEnvironmentDetector.detectActiveProfiles();

    if (activeEnvironments.isEmpty()) {
      log.info("No active Wise profile detected. Defaulting to 'WISE'.");
      activeEnvironments.add(WiseProfile.WISE);
    } else {
      log.info("Active Wise profiles detected are '" + activeEnvironments.stream().map(Enum::name).collect(Collectors.joining(",")) + "'.");
    }

    WiseEnvironment.init(activeEnvironments);

    environment.getPropertySources().addLast(new WiseEnvironmentDefaultsPropertySource());
  }
}
