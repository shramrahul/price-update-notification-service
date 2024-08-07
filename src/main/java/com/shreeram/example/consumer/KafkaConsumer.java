package com.shreeram.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@KafkaListener(topics = "product-price-update", groupId = "product-price-update-group")
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaHandler
  //  @SendTo("product-price-update-success")
    public String listen(String message) throws Exception {
        System.out.println(message);
        messagingTemplate.convertAndSend("/topic/messages", message);
        return message;
    }


}
