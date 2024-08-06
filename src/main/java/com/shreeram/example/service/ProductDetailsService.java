package com.shreeram.example.service;

import com.shreeram.example.exception.ProductNotFoundException;
import com.shreeram.example.model.KafkaMessage;
import com.shreeram.example.model.ProductDetails;
import com.shreeram.example.repository.ProductDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;

    public ProductDetailsService(ProductDetailsRepository productDetailsRepository) {
        this.productDetailsRepository = productDetailsRepository;
    }

    public Optional<ProductDetails> getAllProductsById(String id) {
        return productDetailsRepository.findById(id);
    }

    public void updateProductPrice(KafkaMessage kafkaMessage) {
        String id = kafkaMessage.getProductId();
        Optional<ProductDetails> optionalProduct = productDetailsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductDetails productDetails = optionalProduct.get();
            productDetails.setPrice(kafkaMessage.getNewPrice());
            productDetailsRepository.save(productDetails);
            log.info("Price updated successfully for product id: {}", id);
        } else {
            log.error("product not found for product id: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

}
