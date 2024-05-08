package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Option;
import jakarta.validation.constraints.NotNull;

public record CreateOptionRequest(
        @NotNull(message = "name 파라미터가 누락 되었습니다.")
        String name,
        int price,
        @NotNull(message = "amount 파라미터가 누락 되었습니다.")
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
