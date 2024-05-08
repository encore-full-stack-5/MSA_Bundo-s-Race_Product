package com.example.bundosRace.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record sellProduct(
        @NotNull(message = "productId 파라미터가 누락 되었습니다.")
        Long productId,
        @NotNull(message = "count 파라미터가 누락 되었습니다.")
        Integer count,
        @NotNull(message = "optionId 파라미터가 누락 되었습니다.")
        List<Long> optionIds
) {
}