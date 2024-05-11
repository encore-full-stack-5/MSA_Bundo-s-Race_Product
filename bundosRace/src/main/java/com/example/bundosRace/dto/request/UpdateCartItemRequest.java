package com.example.bundosRace.dto.request;

public record UpdateCartItemRequest(
        Long productId,
        String productName,
        Integer productPrice,
        Integer productDiscount,
        Integer productDelivery
) {
}
