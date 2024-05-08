package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.Seller;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateSellerRequest(
        @NotNull(message = "name 파라미터가 누락 되었습니다.")
        String name,
        @NotNull(message = "uid 파라미터가 누락 되었습니다.")
        String uid
) {
    public Seller toEntity() {
        return Seller.builder()
                .name(name)
                .uid(uid)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
