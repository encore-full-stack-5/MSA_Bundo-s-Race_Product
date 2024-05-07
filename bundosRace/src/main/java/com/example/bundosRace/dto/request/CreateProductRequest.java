package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.OptionGroup;
import com.example.bundosRace.domain.Product;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateProductRequest
        (
                @NotEmpty(message = "sellerId 파라미터가 누락 되었습니다.")
                Long sellerId,
                @NotEmpty(message = "categoryId 파라미터가 누락 되었습니다.")
                Long categoryId,
                @NotEmpty(message = "name 파라미터가 누락 되었습니다.")
                String name,
                String images,
                @NotEmpty(message = "price 파라미터가 누락 되었습니다.")
                Integer price,
                Integer deliveryPrice,
                @NotEmpty(message = "amount 파라미터가 누락 되었습니다.")
                Integer amount,
                @NotEmpty(message = "status 파라미터가 누락 되었습니다.")
                Integer status
        ) {

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .images(images)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .amount(amount)
                .status(status)
                .build();
    }


}
