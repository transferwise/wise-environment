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
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.TEST));
    assertTrue(WiseEnvironment.isProfileActive(WiseProfile.INTEGRATION_TEST));
    assertFalse(WiseEnvironment.isProfileActive(WiseProfile.UNIT_TEST));
  }

  @Test
  void defaultsCanBeSet() {
    WiseEnvironment.setDefaultProperty("mySource", "mykey", "robot");

    WiseEnvironment.setDefaultProperty("mySource", WiseProfile.TEST, "mykey", "cat");
    assertThat(environment.getProperty("mykey"), equalTo("cat"));
    assertThat(WiseEnvironment.getDefaultPropertyContainer("mykey").getOrigin().getProfile(), equalTo(WiseProfile.TEST));

    WiseEnvironment.setDefaultProperty("mySource", WiseProfile.INTEGRATION_TEST, "mykey", "dog");
    assertThat(environment.getProperty("mykey"), equalTo("dog"));

    assertThat(WiseEnvironment.getDefaultPropertyContainer("mykey").getOrigin().getProfile(), equalTo(
        WiseProfile.INTEGRATION_TEST));

    WiseEnvironment.setDefaultProperties(dsl -> dsl
        .source("mySource")
        .profile(WiseProfile.UNIT_TEST)
        .keyPrefix("prefix.")
        .set("myotherkey", "horse")
        .profile(WiseProfile.TEST)
        .set("myotherkey", "mouse")
        .keyPrefix(null)
        .set("onemorekey", "moose")
    );
    assertThat(environment.getProperty("prefix.myotherkey"), equalTo("mouse"));
    assertThat(environment.getProperty("onemorekey"), equalTo("moose"));

  }
}
