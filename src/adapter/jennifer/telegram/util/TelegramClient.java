package adapter.jennifer.telegram.util;

import adapter.jennifer.telegram.entity.TelegramProperties;
import com.jennifersoft.view.adapter.util.LogUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple Client to send messages
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 11:11 AM.
 */
public class TelegramClient {

    private final String CHAT_ID_FIELD = "chat_id";

    private final String TEXT_FIELD = "text";

    private final String DISABLE_WEBPAGE_VIEW_FIELD = "disable_web_page_preview";


    /**
     * Default connection time out value
     */
    private final int CONNECTION_TIME_OUT	= 5 * 1000;

    /**
     * Default encoding value
     */
    private final String ENCODING			= "UTF-8";

    /**
     * Telegram Properties
     */
    private TelegramProperties telegramProperties;

    /**
     * Event Message
     */
    private String message;

    /**
     *
     * @param message The message itself
     * @param properties the telegram properties
     */
    public TelegramClient(String message, TelegramProperties properties) {
        this.message = message;
        this.telegramProperties = properties;
    }

    public String push() {
        HttpURLConnection connection = null;
        try{
            Map<String, String> params = new LinkedHashMap();
            params.put(CHAT_ID_FIELD, telegramProperties.getChatId());
            params.put(DISABLE_WEBPAGE_VIEW_FIELD, 1 + "");
            params.put(TEXT_FIELD, message);


            byte[] postDataBytes = getQuery(params).getBytes(ENCODING);

            URL url = new URL(telegramProperties.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", ENCODING);
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setConnectTimeout(CONNECTION_TIME_OUT);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(postDataBytes);
            out.flush();
            out.close();

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while ( (line = reader.readLine()) != null )
                response.append(line + "\n");

            reader.close();

            return response.toString();


        }catch (Exception ex) {
            LogUtil.error("Error while pushing the message. Reason : " + ex.toString());
            ex.printStackTrace();
            return null;
        }finally {
            if (connection != null) connection.disconnect();
        }

    }


    private String getQuery(Map<String, String> params) throws UnsupportedEncodingException{
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), ENCODING));
            postData.append("=");
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), ENCODING));
        }
        return postData.toString();
    }
}
