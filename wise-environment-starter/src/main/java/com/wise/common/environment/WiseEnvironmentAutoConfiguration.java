package com.wise.common.environment;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class WiseEnvironmentAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(WiseEnvironmentProperties.class)
  @ConfigurationProperties(prefix = "wise.environment.core", ignoreUnknownFields = false)
  public WiseEnvironmentProperties wiseEnvironmentProperties() {
    return new WiseEnvironmentProperties();
  }

  @Bean
  @ConditionalOnMissingBean(WiseEnvironment.class)
  public DefaultWiseEnvironment wiseEnvironment(WiseEnvironmentProperties wiseEnvironmentProperties) {
    return new DefaultWiseEnvironment(wiseEnvironmentProperties);
  }
}
