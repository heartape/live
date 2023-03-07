# Adapter for media server


[![releases](https://img.shields.io/github/v/release/heartape/live-spring-boot-starter.svg?logo=github)](https://github.com/heartape/live/releases)
[![Maven Central](https://img.shields.io/maven-central/v/com.heartape/live-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.heartape%20AND%20a:live)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


Easily connect third-party streaming media servers to applications


## Getting Started

spring boot configuration
```yaml
spring:
  live:
    type: srs
    loadbalancer: false
    app: live
    protocol: rtmp
    host: 127.0.0.1
```

## Support

| server     | support |
|------------|---------|
| SRS        | yes     |
| ZLMediaKit | no      |
| built-in   | todo    |
