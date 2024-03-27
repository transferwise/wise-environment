package com.wise.common.environment;

public class DefaultPropertiesSetterDsl implements PropertiesSetterDsl {

  private String source;
  private WiseEnvironment wiseEnvironment;

  private String keyPrefix;

  private Dsl0 dsl0 = new Dsl0Impl();
  private Dsl1 dsl1 = new Dsl1Impl();
  private Dsl2 dsl2 = new Dsl2Impl();

  @Override
  public Dsl0 source(String source) {
    this.source = source;
    return dsl0;
  }

  class Dsl0Impl implements Dsl0 {

    @Override
    public Dsl2 environment(WiseEnvironment wiseEnvironment) {
      DefaultPropertiesSetterDsl.this.wiseEnvironment = wiseEnvironment;
      return dsl2;
    }

    @Override
    public Dsl1 keyPrefix(String keyPrefix) {
      DefaultPropertiesSetterDsl.this.keyPrefix = keyPrefix;
      return dsl1;
    }
  }

  class Dsl1Impl implements Dsl1 {

    @Override
    public Dsl2 environment(WiseEnvironment wiseEnvironment) {
      DefaultPropertiesSetterDsl.this.wiseEnvironment = wiseEnvironment;
      return dsl2;
    }
  }

  class Dsl2Impl implements Dsl2 {

    @Override
    public Dsl2 environment(WiseEnvironment wiseEnvironment) {
      DefaultPropertiesSetterDsl.this.wiseEnvironment = wiseEnvironment;
      return this;
    }

    @Override
    public Dsl2 keyPrefix(String keyPrefix) {
      DefaultPropertiesSetterDsl.this.keyPrefix = keyPrefix;
      return this;
    }

    @Override
    public Dsl2 set(String name, Object value) {
      WiseEnvironment.setDefaultProperty(source, wiseEnvironment, keyPrefix == null ? name : keyPrefix + name, value);
      return this;
    }
  }
}
