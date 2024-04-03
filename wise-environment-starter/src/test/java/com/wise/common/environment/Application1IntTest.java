package com.wise.common.environment;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("application1")
public class Application1IntTest {

  @Test
  void applicationIsConfigured() {
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.PCI_PLASTIC_STAGING));
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.STAGING));
  }

}
