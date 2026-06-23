package com.xa.keepalive;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Actuator Health Indicator for XA KeepAlive configuration.
 * Shows whether keepalive settings are active and their values.
 */
@Component
public class XaKeepAliveHealthIndicator implements HealthIndicator {

    private final XaKeepAliveProperties properties;

    public XaKeepAliveHealthIndicator(XaKeepAliveProperties properties) {
        this.properties = properties;
    }

    @Override
    public Health health() {
        if (!properties.isEnabled()) {
            return Health.down()
                    .withDetail("status", "DISABLED")
                    .withDetail("reason", "XA KeepAlive is disabled in configuration")
                    .build();
        }

        return Health.up()
                .withDetail("status", "ENABLED")
                .withDetail("connectTimeout", properties.getConnectTimeout() + " ms")
                .withDetail("readTimeout", properties.getReadTimeout() + " ms")
                .withDetail("tcpNoDelay", properties.isTcpNoDelay())
                .withDetail("localTransactionMode", properties.isLocalTransactionMode())
                .build();
    }
}