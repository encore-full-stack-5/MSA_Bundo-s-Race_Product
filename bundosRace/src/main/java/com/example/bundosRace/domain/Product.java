package com.example.bundosRace.domain;

import com.example.bundosRace.core.error.ExpectedError;
import com.example.bundosRace.core.error.UnexpectedError;
import com.example.bundosRace.core.util.JsonStringListConverter;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.transaction.annotation.Transactional;

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

    @Column(name = "images", columnDefinition = "jsonb")
    @Convert(converter = JsonStringListConverter.class)
    private List<String> images;

    @Column(name ="price")
    private Integer price;

    @Column(name ="discount_rate")
    private int discountRate;

    @Column(name ="delivery_price")
    private int deliveryPrice;

    @Column(name ="is_deleted")
    @ColumnDefault("false")
    @Builder.Default
    private Boolean isDeleted = false;

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
        optionGroup.setProduct(this);
    }

    public void removeOptionGroup(OptionGroup optionGroup) {
        this.optionGroups.remove(optionGroup);
        optionGroup.setProduct(null);
    }

    public void updateEntity(UpdateProductRequest request) {
        if(request.name() != null) name = request.name();
        if(request.description() != null) description = request.description();
        if(request.price() != null) price = request.price();
        if(request.discountRate() != null) discountRate = request.discountRate();
        if(request.deliveryPrice() != null) deliveryPrice = request.deliveryPrice();
        if(request.status() != null) status = request.status();
    }

    public void sell(int count, List<Long> optionIds) {
        if (this.amount < count) {
            throw new ExpectedError.ResourceNotFoundException(("재고가 부족합니다."));
        }
        this.amount -= count;
        this.sellCount += count;
        if(optionIds != null)  {
            optionIds.forEach(optionId -> sellOption(optionId, count));
        }
    }

    public void sellOption(Long optionId, int count) {
        OptionGroup optionGroup = optionGroups.stream()
                .filter(og -> og.getOptions().stream().anyMatch(o -> o.getId().equals(optionId)))
                .findFirst()
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 "+ optionId +" 옵션이 포함된 옵션그룹이 존재하지 않습니다."));
        optionGroup.sellOption(optionId, count);
    }

    public void delete() {
        this.isDeleted = true;
    }
}
