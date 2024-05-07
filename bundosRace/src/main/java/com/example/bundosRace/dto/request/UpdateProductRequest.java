package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Product;

import java.util.List;

public record UpdateProductRequest(
        String name,
        String description,
        Integer price,
        Integer discountRate,
        Integer deliveryPrice,
        Integer status
) {

    public void updateEntity(Product product) {
        if(name != null) product.setName(name);
        if(description != null) product.setDescription(description);
        if(price != null) product.setPrice(price);
        if(discountRate != null) product.setDiscountRate(discountRate);
        if(deliveryPrice != null) product.setDeliveryPrice(deliveryPrice);
        if(status != null) product.setStatus(status);
    }
}
