package com.example.bundosRace.domain;

import com.example.bundosRace.core.util.JsonStringListConverter;
import jakarta.persistence.*;
import lombok.*;
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

    @Setter
    @Column(name ="product_name")
    private String name;

    @Setter
    @Column(name ="description")
    private String description;

    @Column(name = "images", columnDefinition = "jsonb")
    @Convert(converter = JsonStringListConverter.class)
    private List<String> images;

    @Setter
    @Column(name ="price")
    private Integer price;

    @Setter
    @Column(name ="discount_rate")
    private int discountRate;

    @Setter
    @Column(name ="delivery_price")
    private int deliveryPrice;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    @Column(name ="amount")
    private Integer amount;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name ="status")
    private Integer status;

    @Column(name ="sell_count")
    private int sellCount;

    @Setter
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    public void addOptionGroup(OptionGroup optionGroup) {
        this.optionGroups.add(optionGroup);
        optionGroup.setProduct(this);  // 옵션 그룹에 상품 참조 설정
    }

    public void removeOptionGroup(OptionGroup optionGroup) {
        this.optionGroups.remove(optionGroup);
        optionGroup.setProduct(null);  // 옵션 그룹의 상품 참조 해제
    }

}
