package com.shreeram.example.utils;

import com.shreeram.example.model.KafkaMessage;

public class MessageUtils {
    public static KafkaMessage parseMessage(String message) {

        String [] messageArray = message.replace("\"", "").split(",");
        return KafkaMessage.builder()
                .productId(messageArray[0])
                .newPrice(Double.parseDouble(messageArray[1])).build();
    }
}
