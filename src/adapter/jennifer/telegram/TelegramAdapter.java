package adapter.jennifer.telegram;

import adapter.jennifer.telegram.entity.TelegramProperties;
import adapter.jennifer.telegram.entity.TelegramResponse;
import adapter.jennifer.telegram.util.ConfUtil;
import adapter.jennifer.telegram.util.TelegramClient;
import com.google.gson.Gson;
import com.jennifersoft.view.adapter.JenniferAdapter;
import com.jennifersoft.view.adapter.JenniferModel;
import com.jennifersoft.view.adapter.model.JenniferEvent;
import com.jennifersoft.view.adapter.util.LogUtil;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adapter Main Logic
 * @author Khalid Saeed <khalid@jennifersoft.com>
 * @Created 11/22/17 10:39 AM.
 */
public class TelegramAdapter implements JenniferAdapter{

    /**
     * Format the date and time
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Override
    public void on(JenniferModel[] jenniferModels) {
        TelegramProperties telegramProperties = ConfUtil.getTelegramProperties();
        for(int i = 0; i < jenniferModels.length; i++) {
            JenniferEvent event = (JenniferEvent) jenniferModels[i];
            String message = jenniferEventToString(event);
            TelegramClient client = new TelegramClient(message, telegramProperties);
            String result = client.push();
            if (result == null) { //exception occurred
                LogUtil.info("Message was not sent, please  check the adapter log file for more information");
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
     * @param eventModel the event model
     * @return event model as string (Slack message body)
     */
    private String jenniferEventToString(JenniferEvent eventModel){
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("The following event "+eventModel.getErrorType()+" was caught by JENNIFER\n");
        messageBody.append("Here are some additional details\n");
        messageBody.append("Affected Domain [ID:NAME]: " + eventModel.getDomainId() + ":" + eventModel.getDomainName()+"\n");
        messageBody.append("Affected Instance [ID:NAME]: " + eventModel.getInstanceId() + ":" + eventModel.getInstanceName()+"\n");
        String message = eventModel.getMessage().length() == 0 ? "None" :eventModel.getMessage();
        messageBody.append("Message : " + message + "\n");
        String detailedMessage = eventModel.getDetailMessage().length() == 0 ? "None" :eventModel.getDetailMessage();
        messageBody.append("Detailed Message : " + detailedMessage + "\n");
        String serviceName = eventModel.getServiceName().length() == 0 ? "Not available" : eventModel.getServiceName();
        messageBody.append("Service Name : " + serviceName + "\n");
        String transactionId = eventModel.getTxId() == 0 ? "Not available" : eventModel.getTxId() + "";
        messageBody.append("Transaction Id: " + transactionId + "\n");
        String metricsName = eventModel.getMetricsName().length() == 0 ? "Not available" : eventModel.getMetricsName();
        messageBody.append("Metrics Name : " + metricsName + "\n");
        Date d = new Date(eventModel.getTime());
        messageBody.append("Event Time [Raw:Human Readable]: " + eventModel.getTime()  + ":" + sdf.format(d)+"\n");
        messageBody.append("\nThis message was created automatically by JENNIFER Adapter");

        return messageBody.toString();
    }
}
