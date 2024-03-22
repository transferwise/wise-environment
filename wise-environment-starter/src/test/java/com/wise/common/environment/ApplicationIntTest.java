package com.wise.common.environment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(classes = TestApplication.class)
public class ApplicationIntTest {

  @Autowired
  private Environment environment;

  @Test
  void applicationIsConfigured() {
    assertTrue(WiseEnvironment.isEnvironmentActive(WiseEnvironment.TEST));
    assertTrue(WiseEnvironment.isEnvironmentActive(WiseEnvironment.INTEGRATION_TEST));
    assertFalse(WiseEnvironment.isEnvironmentActive(WiseEnvironment.UNIT_TEST));
  }

  @Test
  void defaultsCanBeSet() {
    WiseEnvironment.setDefaultProperty("mykey", "robot");

    WiseEnvironment.setDefaultProperty(WiseEnvironment.TEST, "mykey", "cat");
    assertThat(environment.getProperty("mykey"), equalTo("cat"));

    WiseEnvironment.setDefaultProperty(WiseEnvironment.INTEGRATION_TEST, "mykey", "dog");
    assertThat(environment.getProperty("mykey"), equalTo("dog"));
  }
}
