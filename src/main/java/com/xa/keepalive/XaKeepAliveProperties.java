package com.xa.keepalive;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for XA KeepAlive Starter.
 * Users can override these in application.properties.
 */
@ConfigurationProperties(prefix = "xa.keepalive")
public class XaKeepAliveProperties {

    private boolean enabled = true;
    private int connectTimeout = 60000;      // 60 seconds
    private int readTimeout = 600000;        // 10 minutes
    private boolean tcpNoDelay = true;
    private boolean localTransactionMode = true;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }

    public int getReadTimeout() { return readTimeout; }
    public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }

    public boolean isTcpNoDelay() { return tcpNoDelay; }
    public void setTcpNoDelay(boolean tcpNoDelay) { this.tcpNoDelay = tcpNoDelay; }

    public boolean isLocalTransactionMode() { return localTransactionMode; }
    public void setLocalTransactionMode(boolean localTransactionMode) { this.localTransactionMode = localTransactionMode; }
}