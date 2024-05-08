package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final ProductsService productService;

    @PostMapping
    public ResponseEntity<?> createSeller(
            @Valid @RequestBody CreateSellerRequest request
    ) {
        productService.createSeller(request);
        return ResponseEntity.ok("success");
    }
}
