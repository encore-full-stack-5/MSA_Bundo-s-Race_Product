package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Seller;

import java.time.LocalDateTime;

public record CreateSellerRequest(
        String name
) {
    public Seller toEntity() {
        return Seller.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
