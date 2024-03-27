package com.wise.common.environment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("application2")
public class Application2IntTest {

  @Test
  void applicationIsConfigured() {
    assertTrue(WiseEnvironment.isEnvironmentActive(WiseEnvironment.PCI_PLASTIC_STAGING));
    assertTrue(WiseEnvironment.isEnvironmentActive(WiseEnvironment.STAGING));
    assertTrue(WiseEnvironment.isEnvironmentActive(WiseEnvironment.DEVELOPMENT));
    assertFalse(WiseEnvironment.isEnvironmentActive(WiseEnvironment.DEVELOPMENT_AGAINST_CUSTOM_ENVIRONMENT));
  }

}
