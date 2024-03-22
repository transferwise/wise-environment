package com.wise.common.environment.impl;

import com.wise.common.environment.WiseEnvironment;
import java.util.List;

public interface WiseEnvironmentDetector {

  List<WiseEnvironment> detect();
}
