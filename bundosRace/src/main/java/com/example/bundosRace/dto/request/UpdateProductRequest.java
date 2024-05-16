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
        Integer reviewCount,
        Integer status // 1 판매중 2 매진 3 숨기기
) {

}
