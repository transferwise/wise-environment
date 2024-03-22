package com.wise.common.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestApplication.class)
public class ApplicationIntTest {

  @Autowired
  private WiseEnvironment wiseEnvironment;

  @Autowired
  private TestProperties testProperties;

  @Test
  void applicationIsConfigured() {
    assertTrue(wiseEnvironment.isIntegrationTest());
  }

  @Test
  void defaultEnvVariablesAreApplied() {
    // Default set via ENV can be overridden via application.yml
    assertEquals("blah", testProperties.getValue1());
    // Default set via ENV is applied
    assertEquals("foo", testProperties.getValue2());
    // Environment variables still overwrite everything
    assertEquals("foo", testProperties.getValue3());
    // Defaults can be used through system properties as well
    assertEquals("foo", testProperties.getValue4());
  }
}
