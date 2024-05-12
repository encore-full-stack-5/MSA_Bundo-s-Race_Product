package com.example.bundosRace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public record SellProductsRequest(
        @NotEmpty(message = "sellProducts 파라미터가 누락 되었습니다.")
        List<sellProduct> sellProducts
) {
}
