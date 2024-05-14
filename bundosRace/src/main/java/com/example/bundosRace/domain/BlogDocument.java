package com.example.bundosRace.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

@Document(
        indexName = "blogs",
        writeTypeHint = WriteTypeHint.FALSE
)
@Getter
@Builder
public class BlogDocument {
    @Id
    long id;
    String name;
    String description;
    String url;
}
