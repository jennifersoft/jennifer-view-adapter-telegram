package adapter.jennifer.telegram.entity;

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

    /**
     * Create Telegram Bot API Url with the token
     * @return formatted URL
     */
    public String getUrl() {
        return String.format("%s%s/sendMessage", API_PREFIX, getToken());
    }
}
