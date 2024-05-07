package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.service.ProductsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
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


}
