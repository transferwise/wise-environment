package com.wise.common.environment;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public enum WiseProfile {
  WISE,

  PRODUCTION(WISE),
  PCI_PLASTIC_PRODUCTION(PRODUCTION),

  STAGING(WISE),
  CUSTOM_ENVIRONMENT(STAGING),
  SANDBOX(STAGING),
  PCI_PLASTIC_STAGING(STAGING),

  TEST(WISE),
  INTEGRATION_TEST(TEST),
  UNIT_TEST(TEST),

  DEVELOPMENT(WISE),

  DEVELOPMENT_AGAINST_CUSTOM_ENVIRONMENT(DEVELOPMENT);

  private WiseProfile parent;

  WiseProfile() {
  }

  WiseProfile(WiseProfile parent) {
    this.parent = parent;
  }

  public WiseProfile parent() {
    return parent;
  }

  private static final Map<String, WiseProfile> NAME_INDEX =
      Arrays.stream(WiseProfile.values()).collect(Collectors.toMap(we -> we.name().toLowerCase(), we -> we));

  public static WiseProfile getByName(String name) {
    return StringUtils.isAllLowerCase(name) ? NAME_INDEX.get(name) : NAME_INDEX.get(name.toLowerCase());
  }
}
