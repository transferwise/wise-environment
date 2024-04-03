package com.wise.common.environment;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class WiseEnvironment {

  private static WiseActiveProfilesProvider wiseActiveProfilesProvider;

  static final Map<WiseProfile, Map<String, PropertyContainer>> defaultProperties = new ConcurrentHashMap<>();
  private static final Map<List<WiseProfile>, String[]> defaultPropertyNamesCache = new ConcurrentHashMap<>();

  public static void init(List<WiseProfile> activeProfiles) {
    wiseActiveProfilesProvider = new CachingWiseActiveProfilesProvider(activeProfiles);
  }

  public static void setDefaultProperty(String source, String name, Object value) {
    setDefaultProperty(source, WiseProfile.WISE, name, value);
  }

  public static void setDefaultProperty(String source, WiseProfile wiseProfile, String name, Object value) {
    defaultProperties
        .computeIfAbsent(wiseProfile, k -> new ConcurrentHashMap<>())
        .put(name, new PropertyContainer(source, wiseProfile, value));

    defaultPropertyNamesCache.clear();
  }

  public static List<WiseProfile> getActiveProfiles() {
    return wiseActiveProfilesProvider.getActiveProfiles();
  }

  public static boolean isProfileActive(WiseProfile profile) {
    return wiseActiveProfilesProvider.isProfileActive(profile);
  }

  static PropertyContainer getDefaultPropertyContainer(String name) {
    var activeProfiles = getActiveProfiles();

    if (activeProfiles != null) {
      for (var activeProfile : activeProfiles) {
        while (activeProfile != null) {
          var props = defaultProperties.get(activeProfile);
          if (props != null) {
            var value = props.get(name);
            if (value != null) {
              return value;
            }
          }

          activeProfile = activeProfile.parent();
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
    var activeProfiles = getActiveProfiles();

    if (activeProfiles == null) {
      return new String[0];
    }

    return defaultPropertyNamesCache.computeIfAbsent(activeProfiles, k -> {
      var result = new HashSet<String>();

      for (var activeProfile : activeProfiles) {
        while (activeProfile != null) {
          var props = defaultProperties.get(activeProfile);
          if (props != null) {
            result.addAll(props.keySet());
          }

          activeProfile = activeProfile.parent();
        }
      }
      return result.toArray(new String[0]);
    });
  }

  public static void setDefaultProperties(Consumer<PropertiesSetterDsl> dslConsumer) {
    dslConsumer.accept(new DefaultPropertiesSetterDsl());
  }
}
