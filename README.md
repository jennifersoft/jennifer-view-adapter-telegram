## Important Notice
This adapter is not compatible with JENNIFER Server  prior to **`5.2.3`** 

## Overview
An adapter to send JENNIFER EVENT notification to [Telegram](https://telegram.org/). You can also check the [Slack Adapter](https://github.com/jennifersoft/jennifer-view-adapter-slack)

## Requirements
To use this adapter you will need two things: 
* Token
* Chat ID

#### Getting The Token and Chat ID
* Talk to the BotFather and use the /newbot command to create a new bot. The BotFather will give you a token. Keep It for later.
* Next you need the chat ID. To obtain the chat ID, first use your telegram client to send any message to your new bot.
* Then call the `getUpdates` methods in Telegram API by opening your browser and navigate to the following link. (Make sure to replace `${TOKEN_HERE}` with your actual token)
```
https://api.telegram.org/bot{TOKEN_HERE}/getUpdates
```
* You should see reponse like follow. Make Note of the chat ID is you will need it

<img width="395" alt="telegram_chat_id_example" src="https://user-images.githubusercontent.com/3861725/33116235-5fecf274-cfa7-11e7-8b8a-8598b56c67ff.png">



* Alternatively, you can use the `ChatIdHelper` class located in the `util` package. Run the main method of this class passing your token.
* You should see response message like follow : 
```
Chat ID is [452396036]
```

* If you did not see this reponse. Make sure that you already sent any message to your newly created bot, then try again.
 
 
## Adapter Settings
Once you have your token and chat ID you can proceed with the adapter installation/configuration as follow: 

The first step is to register the adapter: 
1. In JENNIFER Client, open the management area and Navigate to  **Extension and Notice** > **Adapter and Plugin**
2. Make sure the adapter tab is selected then click the **[+Add]** button
3. Select **[Event]** from the classifications dropdown.
4. Enter **``telegram``** as the adapter ID.
5. Enter the path to the adapter JAR file ``jennifer-view-adapter-telegram.jar`` or upload the JAR file from you local machine.
6. Enter the adapter class name ``adapter.jennifer.telegram.TelegramAdapter`` and save the settings.
 

<img width="799" alt="telegram_adapter_settings" src="https://user-images.githubusercontent.com/3861725/33116460-24d5ff40-cfa8-11e7-9843-6feec8738803.png">


### Options ##

The following table shows the required options for this adapter

| Key           | Required      | Description | Example |
| ------------- |:-------------:|:-------------:|:-------------:|
| token | YES           | Set the token you obtained from the BotFather here | 0980943:ojasdasctlkjlkjasdlnasdnxcmv|
| chat_id | YES           | Set the chat ID here  | 45623121|
| proxy_host | NO | If you are using proxy, use this option to set the Proxy Host IP | 10.10.2.2|
| proxy_port | NO | If you are using proxy, use this option to set the  Proxy Port Number | 3128|
