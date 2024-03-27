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
    WiseEnvironment.setDefaultProperty("mySource", "mykey", "robot");

    WiseEnvironment.setDefaultProperty("mySource", WiseEnvironment.TEST, "mykey", "cat");
    assertThat(environment.getProperty("mykey"), equalTo("cat"));
    assertThat(WiseEnvironment.getDefaultPropertyContainer("mykey").getOrigin().getEnvironment(), equalTo(WiseEnvironment.TEST));

    WiseEnvironment.setDefaultProperty("mySource", WiseEnvironment.INTEGRATION_TEST, "mykey", "dog");
    assertThat(environment.getProperty("mykey"), equalTo("dog"));

    assertThat(WiseEnvironment.getDefaultPropertyContainer("mykey").getOrigin().getEnvironment(), equalTo(WiseEnvironment.INTEGRATION_TEST));

    WiseEnvironment.setDefaultProperties(dsl -> dsl
        .source("mySource")
        .environment(WiseEnvironment.UNIT_TEST)
        .keyPrefix("prefix.")
        .set("myotherkey", "horse")
        .environment(WiseEnvironment.TEST)
        .set("myotherkey", "mouse")
        .keyPrefix(null)
        .set("onemorekey", "moose")
    );
    assertThat(environment.getProperty("prefix.myotherkey"), equalTo("mouse"));
    assertThat(environment.getProperty("onemorekey"), equalTo("moose"));

  }
}
