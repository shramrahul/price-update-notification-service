package com.shreeram.example.consumer;

import com.shreeram.example.model.KafkaMessage;
import com.shreeram.example.service.ProductDetailsService;
import com.shreeram.example.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@KafkaListener(topics = "product-price-update", groupId = "product-price-update-group")
public class KafkaConsumer {

    private final ProductDetailsService productDetailsService;
    public KafkaConsumer(ProductDetailsService productDetailsService) {
        this.productDetailsService = productDetailsService;
    }

    @KafkaHandler
  //  @SendTo("product-price-update-success")
    public KafkaMessage listen(String message) {
        KafkaMessage kafkaMessage = MessageUtils.parseMessage(message);
        productDetailsService.updateProductPrice(kafkaMessage);
        return kafkaMessage;
    }


}
