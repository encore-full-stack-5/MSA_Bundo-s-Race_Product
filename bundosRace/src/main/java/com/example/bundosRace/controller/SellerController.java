package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.response.BrandListResponse;
import com.example.bundosRace.service.ProductsService;
import com.example.bundosRace.service.SearchService;
import com.example.bundosRace.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<?> createSeller(
            @Valid @RequestBody CreateSellerRequest request
    ) {
        sellerService.createSeller(request);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandListResponse>> getAllBrand() {
        return ResponseEntity.ok(sellerService.getAllBrand());
    }
}
