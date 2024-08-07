package com.shreeram.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {

    private String productId;
    private String productName;
    private String productDescription;
    private String vendorName;
    private double price;
    private String currency;
}
