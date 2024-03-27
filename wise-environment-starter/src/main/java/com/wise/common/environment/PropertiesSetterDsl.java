package com.wise.common.environment;

public interface PropertiesSetterDsl {

  Dsl0 source(String source);

  interface Dsl0 {

    Dsl2 environment(WiseEnvironment environment);

    Dsl1 keyPrefix(String prefix);
  }

  interface Dsl1 {

    Dsl2 environment(WiseEnvironment wiseEnvironment);

  }

  interface Dsl2 {

    Dsl2 environment(WiseEnvironment environment);

    Dsl2 keyPrefix(String keyPrefix);

    Dsl2 set(String property, Object value);
  }
}
