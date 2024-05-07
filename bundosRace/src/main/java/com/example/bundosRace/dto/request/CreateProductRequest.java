package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.OptionGroup;
import com.example.bundosRace.domain.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record CreateProductRequest
        (
                @NotNull(message = "sellerId 파라미터가 누락 되었습니다.")
                Long sellerId,
                @NotNull(message = "categoryId 파라미터가 누락 되었습니다.")
                Long categoryId,
                @NotEmpty(message = "name 파라미터가 누락 되었습니다.")
                String name,
                @NotNull(message = "price 파라미터가 누락 되었습니다.")
                Integer price,
                @NotNull(message = "amount 파라미터가 누락 되었습니다.")
                Integer amount,
                @NotNull(message = "status 파라미터가 누락 되었습니다.")
                Integer status,
                @NotEmpty(message = "description 파라미터가 누락 되었습니다.")
                String description,
                int discountRate,
                int deliveryPrice,
                List<String> images,
                List<CreateOptionGroupRequest> optionGroups
        ) {

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .images(images)
                .amount(amount)
                .status(status)
                .description(description)
                .discountRate(discountRate)
                .deliveryPrice(deliveryPrice)
                .sellCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
