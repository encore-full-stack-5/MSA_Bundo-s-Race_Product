package com.example.bundosRace.dto.request;

public record UpdateProductRequest(
    String name,
    String description,
    Long price,
    Long categoryId,
    Long optionGroupId
) {
}
