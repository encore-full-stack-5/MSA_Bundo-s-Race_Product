package com.example.bundosRace.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

@Document(
        indexName = "lands",
        writeTypeHint = WriteTypeHint.FALSE
)
@Getter
@Builder
public class LandDocument {
    @Id
    String id;
    String name;
    String description;
    String url;
}
