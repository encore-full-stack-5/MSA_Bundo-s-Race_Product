package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Product;
import jakarta.transaction.Transactional;

import java.util.List;

public record UpdateProductRequest(
        String name,
        String description,
        Integer price,
        Integer discountRate,
        Integer deliveryPrice,
        Integer status
) {

}
