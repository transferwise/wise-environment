package com.wise.common.environment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestProperties {

  private String value1;

  private String value2;

  private String value3;

  private String value4;
}
