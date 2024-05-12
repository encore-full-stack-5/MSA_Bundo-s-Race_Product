package com.example.bundosRace.domain;

import com.example.bundosRace.core.util.JsonStringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;


@Document(
        indexName = "product",
        writeTypeHint = WriteTypeHint.FALSE //
)
@Getter
@Builder
public class ProductForElastic {
    @Id
    private Long id;
//    @Field(name = "name", type = FieldType.Text)
    private String name;
//    @Field(name = "description", type = FieldType.Text)
    private String description;
//    @Field(name = "price", type = FieldType.Integer)
    private Integer price;
//    @Field(name = "discountPrice", type = FieldType.Integer)
    private Integer discountPrice;
//    @Field(name = "discountRate", type = FieldType.Integer)
    private List<String> optionName;
//    @Field(name = "sellCount", type = FieldType.Integer)
    private int sellCount;
//    @Field(name = "brand", type = FieldType.Text)
    private String brand;
//    @Field(name = "categoryName", type = FieldType.Text)
    private String categoryName;

}