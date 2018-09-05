package com.aries.telegram;

import com.aries.extension.data.EventData;
import com.aries.extension.handler.EventHandler;
import com.aries.extension.util.LogUtil;
import com.aries.telegram.entity.TelegramProperties;
import com.aries.telegram.entity.TelegramResponse;
import com.aries.telegram.util.ConfUtil;
import com.aries.telegram.util.TelegramClient;
import com.google.gson.Gson;



import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adapter Main Logic
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 10:39 AM.
 */
public class TelegramAdapter implements EventHandler {

    /**
     * Format the date and time
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public void on(EventData[] eventData) {
        TelegramProperties telegramProperties = ConfUtil.getTelegramProperties();
        for(EventData event: eventData) {

            String message = jenniferEventToString(event);
            TelegramClient client = new TelegramClient(message, telegramProperties);
            String result = client.push();
            if (result == null) { //exception occurred
                LogUtil.info("Error sending the message.");
            }else {
                Gson gson = new Gson();
                TelegramResponse response = gson.fromJson(result, TelegramResponse.class);
                if (!response.isOk()) { //Telegram did not return true:ok
                    LogUtil.info(String.format("Message was not sent, Status Code From Telegram [%d]. Response Message [%s]", response.getError_code(), response.getDescription()));
                }
            }
        }
    }


    /**
     * String representation of the event to be used as the slack message body
     * @param event the event model
     * @return event model as string (Slack message body)
     */
    private String jenniferEventToString(EventData event){
        StringBuilder messageBody = new StringBuilder();
        messageBody.append(String.format("The following event [%s] was caught by JENNIFER. %n",  event.errorType));
        messageBody.append("Here are some additional details\n");
        messageBody.append(String.format("Domain ID: %d%n", event.domainId));
        messageBody.append(String.format("Instance Name: %s%n", event.instanceName));
        messageBody.append(String.format("Transaction ID: %d%n", event.txid));
        messageBody.append(String.format("Service Name: %s%n", event.serviceName));
        messageBody.append(String.format("Error Type: %s%n", event.errorType));
        messageBody.append(String.format("Error Level: %s%n", event.eventLevel));
        messageBody.append(String.format("Error Time: %s%n", sdf.format(new Date(event.time))));

        messageBody.append("\nThis is an auto generated message");

        return messageBody.toString();
    }
}
