package com.xa.keepalive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Auto-configuration for XA KeepAlive functionality.
 * Applies best-practice keep-alive and timeout settings for Oracle XA datasources.
 */
@Configuration
@ConditionalOnClass(name = "com.atomikos.jdbc.AtomikosDataSourceBean")
@ConditionalOnProperty(prefix = "xa.keepalive", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(XaKeepAliveProperties.class)
public class XaKeepAliveAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(XaKeepAliveAutoConfiguration.class);

    @Bean
    public XaDataSourcePostProcessor xaDataSourcePostProcessor(XaKeepAliveProperties properties) {
        log.info("XA KeepAlive Starter activated - applying Oracle XA stability settings");
        return new XaDataSourcePostProcessor(properties);
    }

    @Bean
    public XaKeepAliveHealthIndicator xaKeepAliveHealthIndicator(XaKeepAliveProperties properties) {
        return new XaKeepAliveHealthIndicator(properties);
    }
}