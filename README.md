> [!IMPORTANT]
> **This repository is no longer maintained**
>
> To better serve Wise business and customer needs, this repository will not receive further public updates.
> We have moved the codebase to our internal systems to better align with evolving business requirements.
>
> You can continue to use the existing code under the terms of the Apache 2.0 license, but it will not receive updates or support.
>
> **For continued development and access (Wise employees only):**
> The codebase has moved to a private repository accessible to authorized Wise personnel:
> https://github.com/transferwise/wise-environment-private

# Wise Environment.

![Apache 2](https://img.shields.io/hexpm/l/plug.svg)
![Java 17](https://img.shields.io/badge/Java-17-blue.svg)
![Maven Central](https://badgen.net/maven/v/maven-central/com.transferwise.common/wise-environment)
[![Owners](https://img.shields.io/badge/team-AppEng-blueviolet.svg?logo=wise)](https://transferwise.atlassian.net/wiki/spaces/EKB/pages/2520812116/Application+Engineering+Team) [![Slack](https://img.shields.io/badge/slack-appeng--pub-blue.svg?logo=slack)](https://wise.enterprise.slack.com/archives/C07QSPFLM5X)
> Use the `@application-engineering-on-call` handle on Slack for help.
---

Provides information to other libraries in which environment they are running in.

Allows to set default properties for specific environments.

Typical use case is for various Wise libraries to set environment specific default properties in their `EnvironmentPostProcessor` implementations.

```java
WiseEnvironment.setDefaultProperty("my-library", WiseEnvironment.PRODUCTION, "tw-reliable-jdbc.sslMode", SslMode.VERIFY_FULL);
WiseEnvironment.setDefaultProperty("my-library", WiseEnvironment.STAGING, "tw-reliable-jdbc.sslMode", SslMode.PREFERRED);
WiseEnvironment.setDefaultProperty("my-library", WiseEnvironment.CUSTOM_ENVIRONMENT, "tw-reliable-jdbc.sslMode", SslMode.VERIFY_CA);
```

See `WiseEnvironment` class for other optional use cases. E.g. asking which environments are currently active.

The environments themselves can be hierarchical. In that sense, that if you set a default property to `STAGING`, it would also apply to
`CUSTOM_ENVIRONMENT`, unless a different value is specifically set for `CUSTOM_ENVIRONMENT`.

Also, a convenience DSL is available for setting properties. E.g.

```java
WiseEnvironment.setDefaultProperties(dsl -> dsl
    .source("tw-reliable-jdbc")

    .environment(WiseEnvironment.WISE)
    .set(TW_OBS_BASE_EXTREMUM_CONFIG_PATH, gaugeNames)
    .set("spring.flyway.validate-migration-naming", "true")

    .keyPrefix("tw-reliable-jdbc.")
    .environment(WiseEnvironment.PRODUCTION)
    .set("sslMode", SslMode.VERIFY_FULL)
    .set("requiredSslModeLevel", SslMode.VERIFY_CA)
    .set("requireMinPoolSizePct", 100d)

    .environment(WiseEnvironment.STAGING)
    .set("sslMode", SslMode.VERIFY_FULL)
);
```

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