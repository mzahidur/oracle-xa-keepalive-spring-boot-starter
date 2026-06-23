# Oracle XA KeepAlive Spring Boot Starter

A lightweight, generic Spring Boot starter that automatically applies best-practice keep-alive and timeout settings for Oracle XA datasources (primarily with **Atomikos**).

This helps prevent stale connections and XA recovery errors when applications run behind firewalls, DMZ, or MZ zones.

## Features

- Automatic detection of `AtomikosDataSourceBean`
- Configurable connect/read timeouts
- TCP_NODELAY enabled
- `localTransactionMode=true` for health checks
- Clean logging
- Follows Spring Boot auto-configuration best practices
- Bean validation support

## Usage

### 1. Add Dependency

```xml
<dependency>
    <groupId>io.github.mzahidur</groupId>
    <artifactId>oracle-xa-keepalive-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

### application.properties

```properties
xa.keepalive.enabled=true

# Timeouts (in milliseconds)
xa.keepalive.connect-timeout=60000
xa.keepalive.read-timeout=600000
xa.keepalive.tcp-no-delay=true
xa.keepalive.local-transaction-mode=true
```

### application.yml (Recommended)

```properties
xa:
  keepalive:
    enabled: true
    connect-timeout: 60000      # 60 seconds
    read-timeout: 600000        # 10 minutes
    tcp-no-delay: true
    local-transaction-mode: true
```
