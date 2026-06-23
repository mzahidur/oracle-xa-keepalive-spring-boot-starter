package com.xa.keepalive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Properties;

/**
 * Applies keep-alive and stability settings to Atomikos XA DataSources.
 */
public class XaDataSourcePostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(XaDataSourcePostProcessor.class);

    private final XaKeepAliveProperties properties;

    public XaDataSourcePostProcessor(XaKeepAliveProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Check by class name to avoid direct dependency issues
        if (bean.getClass().getName().contains("AtomikosDataSourceBean")) {
            applyKeepAliveSettings(bean, beanName);
        }
        return bean;
    }

    private void applyKeepAliveSettings(Object bean, String beanName) {
        try {
            // Use reflection to be safe with provided dependencies
            var setXaPropertiesMethod = bean.getClass().getMethod("setXaProperties", Properties.class);
            var getXaPropertiesMethod = bean.getClass().getMethod("getXaProperties");

            Properties xaProps = (Properties) getXaPropertiesMethod.invoke(bean);
            if (xaProps == null) {
                xaProps = new Properties();
            }

            xaProps.put("tcpNoDelay", String.valueOf(properties.isTcpNoDelay()));
            xaProps.put("oracle.net.CONNECT_TIMEOUT", String.valueOf(properties.getConnectTimeout()));
            xaProps.put("oracle.net.READ_TIMEOUT", String.valueOf(properties.getReadTimeout()));

            setXaPropertiesMethod.invoke(bean, xaProps);

            // Set local transaction mode if method exists
            try {
                var setLocalTxMethod = bean.getClass().getMethod("setLocalTransactionMode", boolean.class);
                setLocalTxMethod.invoke(bean, properties.isLocalTransactionMode());
            } catch (Exception ignored) {}

            log.info("✅ [XA-KeepAlive] Successfully applied settings to datasource: {}", beanName);

        } catch (Exception e) {
            log.warn("⚠️ [XA-KeepAlive] Failed to apply settings to {}: {}", beanName, e.getMessage());
        }
    }
}