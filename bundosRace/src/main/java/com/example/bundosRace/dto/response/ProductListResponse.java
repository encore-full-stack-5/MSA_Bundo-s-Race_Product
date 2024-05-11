package com.example.bundosRace.dto.response;
import com.example.bundosRace.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponse {
    private Long productId;
    private String image;
    private int price;
    private int discountRate;
    private String productName;
    private int status;
    private String sellerName;
    private LocalDateTime createdAt;
    private int deliveryPrice;

    public static ProductListResponse fromEntity(Product product){
        return ProductListResponse.builder()
                .productId(product.getId())
                .image(product.getImages().get(0))//0번째가 대표이미지
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .productName(product.getName())
                .status(product.getStatus())
                .sellerName(product.getSeller().getName())
                .createdAt(product.getCreatedAt())
                .deliveryPrice(product.getDeliveryPrice())
                .build();
    }
}