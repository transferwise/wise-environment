# Wise Environment.

![Apache 2](https://img.shields.io/hexpm/l/plug.svg)
![Java 11](https://img.shields.io/badge/Java-11-blue.svg)
![Maven Central](https://badgen.net/maven/v/maven-central/com.transferwise.common/wise-environment)
[![Owners](https://img.shields.io/badge/team-AppEng-blueviolet.svg?logo=wise)](https://transferwise.atlassian.net/wiki/spaces/EKB/pages/2520812116/Application+Engineering+Team) [![Slack](https://img.shields.io/badge/slack-sre--guild-blue.svg?logo=slack)](https://app.slack.com/client/T026FB76G/CLR1U8SNS)
> Use the `@application-engineering-on-call` handle on Slack for help.
---

Provides information to other libraries in which environment they are running in.

Allows to set default properties for specific environments.

Typical use case is for various Wise libraries to set environment specific default properties in their `EnvironmentPostProcessor` implementations.

```java
WiseEnvironment.setDefaultProperty(WiseEnvironment.PRODUCTION, "tw-reliable-jdbc.sslMode", SslMode.VERIFY_FULL);
WiseEnvironment.setDefaultProperty(WiseEnvironment.STAGING, "tw-reliable-jdbc.sslMode", SslMode.PREFERRED);
WiseEnvironment.setDefaultProperty(WiseEnvironment.CUSTOM_ENVIRONMENT, "tw-reliable-jdbc.sslMode", SslMode.VERIFY_CA);
```

See `WiseEnvironment` class for other optional use cases. E.g. asking which environments are currently active.

## License
Copyright 2024 TransferWise Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.