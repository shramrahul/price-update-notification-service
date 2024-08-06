package com.shreeram.example.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ProductDetailsExceptionHandler {

    private static final String PRICE_CHANGE_FAILURE_TOPIC = "product-price-update-failure";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductDetailsExceptionHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public void handleKafkaFailedException(ProductNotFoundException productNotFoundException) {
        String message = null;
        try {
            message = new ObjectMapper().writeValueAsString(productNotFoundException);
            String finalMessage = message;
            kafkaTemplate.send(PRICE_CHANGE_FAILURE_TOPIC, message)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            // Handle failure
                            System.out.println("Unable to send message=[ {} ] due to : {} " + ex.getMessage());
                            log.error("Unable to send message=[ {} ] due to : {} ", finalMessage,  ex.getMessage());
                        } else {
                            // Handle success
                            log.info("Sent message=[ {} ] with offset=[ {} ]", finalMessage, result.getRecordMetadata().offset());
                        }
                    });
        } catch (Exception e) {
            log.error("error: {}", e);
        }

    }
}
