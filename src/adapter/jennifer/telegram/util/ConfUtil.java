package adapter.jennifer.telegram.util;

import adapter.jennifer.telegram.entity.TelegramProperties;
import com.jennifersoft.view.extension.util.PropertyUtil;

/**
 * Adapter Configuration file
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 10:59 AM.
 */

public class ConfUtil {

    /**
     * Adapter ID
     */
    private static final String ADAPTER_ID = "telegram";

    private static final String CHAT_ID_KEY ="chat_id";

    private static final String TOKEN_KEY ="token";


    /**
     * Get a configuration value using the provided key
     * @param key configuration key. Set this key value in the adapter configuration menu in JENNIFER client.
     * @return String configuration value
     */
    public static String getVallue(String key) {
        return PropertyUtil.getValue(ADAPTER_ID, key);
    }

    /**
     * Get the telegram properties
     * @return TelegramProperties instance
     */
    public static TelegramProperties getTelegramProperties() {
        TelegramProperties properties = new TelegramProperties();
        properties.setChatId(getVallue(CHAT_ID_KEY));
        properties.setToken(getVallue(TOKEN_KEY));

        return properties;
    }
}
