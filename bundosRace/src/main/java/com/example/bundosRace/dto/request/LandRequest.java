package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.LandDocument;

public record LandRequest(
        String id,
        String name,
        String description,
        String url
) {

    public LandDocument toDocument() {
        return LandDocument.builder()
                .id(id)
                .name(name)
                .description(description)
                .url(url)
                .build();
    }
}
