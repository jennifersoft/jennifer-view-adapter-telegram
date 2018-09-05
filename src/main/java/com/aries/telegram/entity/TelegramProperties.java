package com.aries.telegram.entity;

/**
 * Telegram Properties and Configurations
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 10:39 AM.
 */
public class TelegramProperties {

    /**
     * Telegram API Prefix
     */
    private final String API_PREFIX = "https://api.telegram.org/bot";

    /**
     * The Token
     */
    private String token;

    /**
     * The chat ID
     */
    private String chatId;

    /**
     * Use Proxy
     */
    private boolean useProxy = false;

    /**
     * Proxy Host
     */
    private String proxyHost;

    /**
     * The proxy port
     */
    private int proxyPort;

    /**
     * Default Constructor
     */
    public TelegramProperties() {}

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * Create Telegram Bot API Url with the token
     * @return formatted URL
     */
    public String getUrl() {
        return String.format("%s%s/sendMessage", API_PREFIX, getToken());
    }
}
