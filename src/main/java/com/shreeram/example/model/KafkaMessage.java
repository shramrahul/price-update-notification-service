package com.shreeram.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaMessage {
    private String productId;
    private double newPrice;
}