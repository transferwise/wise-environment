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
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.PCI_PLASTIC_STAGING));
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.STAGING));
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.DEVELOPMENT));
    assertFalse(WiseEnvironment.isProfileActive(WiseProfile.DEVELOPMENT_AGAINST_CUSTOM_ENVIRONMENT));
  }

}
