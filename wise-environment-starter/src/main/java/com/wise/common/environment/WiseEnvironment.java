package com.wise.common.environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public enum WiseEnvironment {
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

  private WiseEnvironment parent;

  WiseEnvironment() {
  }

  WiseEnvironment(WiseEnvironment parent) {
    this.parent = parent;
  }

  public WiseEnvironment parent() {
    return parent;
  }

  private static final Map<String, WiseEnvironment> NAME_INDEX =
      Arrays.stream(WiseEnvironment.values()).collect(Collectors.toMap(we -> we.name().toLowerCase(), we -> we));

  public static WiseEnvironment getByName(String name) {
    return StringUtils.isAllLowerCase(name) ? NAME_INDEX.get(name) : NAME_INDEX.get(name.toLowerCase());
  }

  private static final Map<WiseEnvironment, Map<String, PropertyContainer>> defaultProperties = new ConcurrentHashMap<>();

  private static final Map<List<WiseEnvironment>, String[]> defaultPropertyNamesCache = new ConcurrentHashMap<>();

  private static WiseEnvironmentProvider wiseEnvironmentProvider;

  public static void init(List<WiseEnvironment> activeEnvironments) {
    wiseEnvironmentProvider = new CachingWiseEnvironmentProvider(activeEnvironments);
  }

  public static void setDefaultProperty(String source, String name, Object value) {
    setDefaultProperty(source, WiseEnvironment.WISE, name, value);
  }

  public static void setDefaultProperty(String source, WiseEnvironment wiseEnvironment, String name, Object value) {
    defaultProperties
        .computeIfAbsent(wiseEnvironment, k -> new ConcurrentHashMap<>())
        .put(name, new PropertyContainer(source, wiseEnvironment, value));

    defaultPropertyNamesCache.clear();
  }

  public static List<WiseEnvironment> getActiveEnvironments() {
    return wiseEnvironmentProvider.getActiveEnvironments();
  }

  public static boolean isEnvironmentActive(WiseEnvironment environment) {
    return wiseEnvironmentProvider.isEnvironmentActive(environment);
  }

  static PropertyContainer getDefaultPropertyContainer(String name) {
    var activeEnvironments = getActiveEnvironments();

    if (activeEnvironments != null) {
      for (var activeEnvironment : activeEnvironments) {
        while (activeEnvironment != null) {
          var envProps = WiseEnvironment.defaultProperties.get(activeEnvironment);
          if (envProps != null) {
            var value = envProps.get(name);
            if (value != null) {
              return value;
            }
          }

          activeEnvironment = activeEnvironment.parent();
        }
      }
    }

    return null;
  }

  public static Object getDefaultProperty(String name) {
    var container = getDefaultPropertyContainer(name);
    return container == null ? null : container.getValue();
  }

  public static String[] getDefaultPropertyNames() {
    var activeEnvironments = getActiveEnvironments();

    if (activeEnvironments == null) {
      return new String[0];
    }

    return defaultPropertyNamesCache.computeIfAbsent(activeEnvironments, k -> {
      var result = new HashSet<String>();

      for (var activeEnvironment : activeEnvironments) {
        while (activeEnvironment != null) {
          var envProps = WiseEnvironment.defaultProperties.get(activeEnvironment);
          if (envProps != null) {
            result.addAll(envProps.keySet());
          }

          activeEnvironment = activeEnvironment.parent();
        }
      }
      return result.toArray(new String[0]);
    });
  }

  public static void setDefaultProperties(Consumer<PropertiesSetterDsl> dslConsumer) {
    dslConsumer.accept(new DefaultPropertiesSetterDsl());
  }
}
