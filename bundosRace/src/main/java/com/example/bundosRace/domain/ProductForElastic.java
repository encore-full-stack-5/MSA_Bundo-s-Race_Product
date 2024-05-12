package com.example.bundosRace.domain;

import com.example.bundosRace.core.util.JsonStringListConverter;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


//@Builder
//@Getter
//@Document(indexName = "product-index")
//public class ProductForElastic {
//    @Id
//    private Long id;
//    private String name;
//    private String description;
//    private Integer price;
//    private Integer discountPrice;
//    private List<String> optionName;
//    private int sellCount;
//    private String brand;
//    private String categoryName;
//
//}