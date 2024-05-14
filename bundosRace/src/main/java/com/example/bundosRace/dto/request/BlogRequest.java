package com.example.bundosRace.dto.request;

import com.example.bundosRace.domain.BlogDocument;
import lombok.Getter;

import java.util.UUID;

public record BlogRequest (
        Long domainId,
        Integer type,
        String name,
        String description,
        String url
) {

    public BlogDocument toDocument() {
        return BlogDocument.builder()
                .id(domainId)
                .domainId(domainId)
                .type(type)
                .name(name)
                .description(description)
                .url(url)
                .build();
    }
}
