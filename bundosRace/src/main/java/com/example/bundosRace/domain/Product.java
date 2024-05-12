package com.example.bundosRace.domain;

import com.example.bundosRace.core.error.ExpectedError;
import com.example.bundosRace.core.error.UnexpectedError;
import com.example.bundosRace.core.util.JsonStringListConverter;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "images", columnDefinition = "jsonb")
    @Convert(converter = JsonStringListConverter.class)
    private List<String> images;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount_rate")
    private int discountRate;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "delivery_price")
    private int deliveryPrice;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    @Builder.Default
    private Boolean isDeleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionGroup> optionGroups = new ArrayList<>();

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    private Integer status; // 1판매중 2매진 3판매중지

    @Column(name = "sell_count")
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

    public void updateEntity(UpdateProductRequest request) {
        if (request.name() != null) name = request.name();
        if (request.description() != null) description = request.description();
        if (request.price() != null) {
            price = request.price();
            updateDiscountPrice();
        }
        if (request.discountRate() != null) {
            discountRate = request.discountRate();
            updateDiscountPrice();
        }
        if (request.deliveryPrice() != null) deliveryPrice = request.deliveryPrice();
        if (request.status() != null) status = request.status();
    }

    public void sell(int count, List<Long> optionIds) {
        if (this.amount < count) {
            throw new ExpectedError.ResourceNotFoundException(("재고가 부족합니다."));
        }
        this.amount -= count;
        this.sellCount += count;
        if (amount == 0) status = 2;
        if (optionIds != null) {
            optionIds.forEach(optionId -> sellOption(optionId, count));
        }
    }

    private void sellOption(Long optionId, int count) {
        OptionGroup optionGroup = optionGroups.stream()
                .filter(og -> og.getOptions().stream().anyMatch(o -> o.getId().equals(optionId)))
                .findFirst()
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 " + optionId + " 옵션이 포함된 옵션그룹이 존재하지 않습니다."));
        optionGroup.sellOption(optionId, count);
    }

    public void validatePossibleSale(List<Long> optionIds, int count, int price) {

        if (status != 1) throw new ExpectedError.BusinessException("판매중인 상품이 아닙니다.");
        if (this.amount < count) throw new ExpectedError.ResourceNotFoundException("상품 재고가 부족합니다.");

        // 옵션 존재 유뮤 확인
        List<Option> options = getOptionGroups().stream()
                .map(OptionGroup::getOptions)
                .filter(ogOptions -> ogOptions.stream().anyMatch(o -> optionIds.contains(o.getId())))
                .flatMap(List::stream)
                .filter(o -> optionIds.contains(o.getId()))
                .toList();

        if (options.size() != optionIds.size()) throw new ExpectedError.BusinessException("옵션 정보가 변경되어서 구매가 불가능합니다..");

        int sum = 0;
        for (Option option : options) {
            sum += option.getPrice();
            option.checkAmount(count); // 옵션수량 체크후 에러 반환
        }

        // 옵션 가격 확인
        int totalPrice = sum + discountPrice;
        if (totalPrice != price) throw new ExpectedError.BusinessException("가격이 변경되어 구매불가능합니다.");
    }

    private void updateDiscountPrice() {
        double discountAmount = price * (discountRate / 100.0);
        this.discountPrice = (int) (price - discountAmount);
    }

    public void delete() {
        status = 3;
        this.isDeleted = true;
    }

//    public ProductForElastic toProductForElastic() {
//        return ProductForElastic.builder()
//                .id(id)
//                .name(name)
//                .description(description)
//                .price(price)
//                .discountPrice(discountPrice)
////                .optionName(optionGroups.stream().flatMap(og -> og.getOptions().stream()).map(Option::getName).toList())
//                .sellCount(sellCount)
//                .brand(seller.getName())
//                .categoryName(category.getName())
//                .build();
//    }
}
