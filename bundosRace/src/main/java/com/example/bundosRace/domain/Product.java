package com.example.bundosRace.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.Type;
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

    @Column(name ="description")
    private String description;

    // 이거 포스트그래ID 컬럼저장방식으로 변경필요 현재는 그냥 String 으로 저장
    @Column(name ="images")
    private String images;

    @Column(name ="price")
    private Integer price;

    @Column(name ="discount_rate")
    private int discountRate;

    @Column(name ="delivery_price")
    private int deliveryPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    @Column(name ="amount")
    private int amount;

    @CreatedDate
    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @Column(name ="status")
    private Integer status;

    @Column(name ="sell_count")
    private Integer sellCount;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

}
