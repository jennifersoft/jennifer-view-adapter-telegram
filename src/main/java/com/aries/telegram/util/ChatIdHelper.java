package com.aries.telegram.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper Class To getChat ID
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 4:43 PM.
 */
public class ChatIdHelper {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String token = args[0];
        String response = callGetUpdates(token);
        if (response != null) {
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            boolean isOk = jsonObject.get("ok").getAsBoolean();
            if (!isOk) {
                System.out.println("Unable to get the chat id");
                System.exit(0);
            }else{
                try{
                    JsonObject firstResult = jsonObject.getAsJsonArray("result").get(0).getAsJsonObject();
                    int chatId = firstResult.get("message").getAsJsonObject().get("chat").getAsJsonObject().get("id").getAsInt();
                    System.out.printf("\nChat ID is [%d]\n", chatId);
                }catch (IndexOutOfBoundsException aiob){
                    System.err.println("[ERROR] Unable to get chat ID. Please make sure you already sent a message to your Bot using your telegram client");
                }

            }
        }
    }

    private static String callGetUpdates(String token) {
        HttpURLConnection connection = null;
        String urlString = String.format("https://api.telegram.org/bot%s/getUpdates", token);
        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while ( (line = reader.readLine()) != null )
                response.append(line + "\n");

            reader.close();

            return response.toString();
        }catch (Exception ex) {
            System.err.println("Error while calling Telegram API. Reason : " + ex.toString());
            return null;
        }finally {
            if (connection != null) connection.disconnect();
        }
    }

    private static void printUsage() {
        System.out.printf("This Utility will help you extract Chat ID.\n");
        System.out.printf("You need to specify the Token you obtained from BotFather as param\n");
        System.out.printf("\tExample:) ./run.sh TOKEN_HERE");
    }


}
