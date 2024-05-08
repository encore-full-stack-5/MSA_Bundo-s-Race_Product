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
    private List<String> images;
    private int price;
    private int discountRate;
    private String productName;
    private int status;
    private String sellerName;
    private LocalDateTime createdAt;

    public ProductListResponse fromEntity(Product product){
        return ProductListResponse.builder()
                .productId(product.getId())
                .images(product.getImages())
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .productName(product.getName())
                .status(product.getStatus())
                .sellerName(product.getSeller().getName())
                .createdAt(product.getCreatedAt())
                .build();
    }
}