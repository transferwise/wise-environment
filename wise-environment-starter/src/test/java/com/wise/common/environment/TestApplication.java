package com.wise.common.environment;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApplication {

  @Bean
  @ConfigurationProperties("wise.environment.test")
  public TestProperties testProperties() {
    return new TestProperties();
  }
}
