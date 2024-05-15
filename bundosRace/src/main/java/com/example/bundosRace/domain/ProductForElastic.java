package com.example.bundosRace.domain;

import com.example.bundosRace.core.util.JsonStringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;


@Document(
        indexName = "products",
        writeTypeHint = WriteTypeHint.FALSE //
)
@Getter
@Builder
public class ProductForElastic {
    @Id
    long id;
    long domainId;
    int type; // 1 상품 2 블로그 3 카페 4 부동산
    String name; // 타이틀
    String description; // 컨텐츠
    String url ; //  http/192.168.0.1/post/2
    String baseUrl;
    private Integer price;
    private Integer discountPrice;
    private List<String> optionName;
    private int sellCount;
    private String brand;
    private String categoryName;

}