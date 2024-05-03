package com.example.bundosRace.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id")
    private Long id;

    @Column(name ="product_name")
    private String name;

    @ElementCollection //리스트를 받으려면 이렇게 받아야 함
    @Column(name ="images")
    private List<String> images;

    @Column(name ="price")
    private Integer price;

    @Column(name ="discount_rate")
    private Integer discountRate;

    @Column(name ="delivery_price")
    private Integer deliveryPrice;

    @OneToMany
    private List<Products_OptionGroups> productsOptionGroups;

    @Column(name ="amount")
    private Integer amount;

    @CreatedDate
    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @Column(name ="status")
    private Integer status;

    @Column(name ="sell_count")
    private Integer sellCount;

    @ManyToOne
    @Column(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @Column(name = "category_id")
    private Category category;

}
