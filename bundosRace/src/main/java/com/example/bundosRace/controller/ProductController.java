package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.dto.response.ProductListResponse;
import com.example.bundosRace.service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateProductRequest request
    ) {
        productService.createProduct(id, request);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getProductCategories() {
        return ResponseEntity.ok(productService.getCategories());
    }

    //update product state
//    @PutMapping("{id}/state")
//    public ResponseEntity<?> updateProductState(@PathVariable("id") int id, @RequestParam("state") int state) {
//        productService.updateProductStatus(id, state);
//        return BaseResponseEntity.ok("success");
//    }

    // 사용자가 등록한 상품 조회


    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductsByUserId(
            @PathVariable("id") Long productId
    ) {
        return ResponseEntity.ok(productService.getProductDetail(productId));
    }

    @GetMapping()
    public ResponseEntity<Page<ProductListResponse>>getProductList(
            @RequestParam(required = false)String category, //필터 넣을 카테고리
            @RequestParam(defaultValue = "0")int page, //각 페이지 시작은 0부터
            @RequestParam(defaultValue = "10")int size, //각 페이지 별 상품 갯수는 10개
            @RequestParam(defaultValue = "name")String sortBy, // 정렬 방식
            @RequestParam(defaultValue = "DESC")Sort.Direction direction){

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(direction,sortBy));

        return ResponseEntity.ok(productService.getProductListByCategoryAndSort(category,pageRequest));
    }


}
