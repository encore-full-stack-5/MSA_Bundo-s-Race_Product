package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Option;

public record CreateOptionRequest(
        Long optionGroupId,
        String name,
        int price,
        Long amount
) {
    public Option toEntity() {
        return Option.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .build();
    }
}
