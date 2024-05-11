package com.example.bundosRace.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ValidateProductRequest(
        List<Long> optionIds,
        @NotNull(message = "price 파라미터가 필요합니다.")
        int price,
        @NotNull(message = "count 파라미터가 필요합니다.")
        int count
) {
}
