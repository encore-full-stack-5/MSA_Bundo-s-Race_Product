package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.OptionGroup;
import com.example.bundosRace.domain.Product;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public record CreateProductRequest
        (
                @NotEmpty(message = "sellerId 파라미터가 누락 되었습니다.")
                Long sellerId,
                @NotEmpty(message = "categoryId 파라미터가 누락 되었습니다.")
                Long categoryId,
                @NotEmpty(message = "name 파라미터가 누락 되었습니다.")
                String name,
                @NotEmpty(message = "price 파라미터가 누락 되었습니다.")
                Integer price,
                @NotEmpty(message = "amount 파라미터가 누락 되었습니다.")
                Integer amount,
                @NotEmpty(message = "status 파라미터가 누락 되었습니다.")
                Integer status,
                int discountRate,
                int deliveryPrice,
                String images,
                List<CreateOptionGroupRequest> optionGroups
        ) {

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .images(images)
                .amount(amount)
                .status(status)
                .discountRate(discountRate)
                .deliveryPrice(deliveryPrice)
                .build();
    }


}
