package com.example.bundosRace.controller;

import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.*;
import com.example.bundosRace.dto.response.ProductListResponse;
import com.example.bundosRace.service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {
        productService.createProduct(request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/{id}/option-groups")
    public ResponseEntity<?> createProductOptionGroup(
            @PathVariable("id") Long productId,
            @Valid @RequestBody CreateOptionGroupRequest request
    ) {
        productService.createProductOptionGroup(productId, request);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/{id}/option-groups/{optionGroupId}/options")
    public ResponseEntity<?> createProductOption(
            @PathVariable("id") Long productId,
            @PathVariable("optionGroupId") Long optionGroupId,
            @Valid @RequestBody CreateOptionRequest request
    ) {
        productService.createProductOption(productId, optionGroupId, request);
        return ResponseEntity.ok("success");
    }


    @GetMapping("/categories")
    public ResponseEntity<?> getProductCategories() {
        return ResponseEntity.ok(productService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductsByUserId(
            @PathVariable("id") Long productId
    ) {
        return ResponseEntity.ok(productService.getProductDetail(productId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        productService.updateProduct(productId, request);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("sell")
    public ResponseEntity<?> sellProduct(
            @RequestBody SellProductsRequest sellProductsRequest
    ) {
        productService.sellProducts(sellProductsRequest);
        return ResponseEntity.ok("success");
    }

    @GetMapping()
    public ResponseEntity<Page<ProductListResponse>>getProductList(
            @RequestParam(required = false, defaultValue = "")Long categoryId, //필터 넣을 카테고리Id
            @RequestParam(required = false , defaultValue = "0")Integer startPrice,
            @RequestParam(required = false, defaultValue = "0")Integer endPrice,
            @RequestParam(required = false ,defaultValue = "")Long sellerId,
            @RequestParam(defaultValue = "1")Integer page, //각 페이지 시작은 0부터
            @RequestParam(defaultValue = "10")Integer size, //각 페이지 별 상품 갯수는 10개
            @RequestParam(defaultValue = "price")String sortBy, // 정렬 방식 1 2 3
            @RequestParam(defaultValue = "DESC")Sort.Direction direction//오름차순 내림차순
            ){

        Sort sort = switch (sortBy) {
            case "reviewCount" -> Sort.by(direction, "reviewCount"); // 리뷰 순
            case "price" -> Sort.by(direction, "price"); // 가격 순
            case "createdAt" -> Sort.by(direction, "createdAt"); // 등록일 순
            default -> Sort.by(direction, sortBy); // 기본값 또는 예상치 못한 값에 대한 처리
        };

       Pageable pageable = PageRequest.of(page -1 ,size, sort);
//        Pageable pageable = PageRequest.of(page -1 ,size);


        return ResponseEntity.ok(productService.getProductListByCategoryAndSort(categoryId , startPrice , endPrice , sellerId , pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("id") Long productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("success");
    }

    @GetMapping("{id}/validate")
    public ResponseEntity<?> validateProduct(
            @PathVariable("id") Long productId,
            @Validated @RequestBody ValidateProductRequest request
    ) {
        productService.validateProduct(productId, request);
        return ResponseEntity.ok("success");
    }
}
