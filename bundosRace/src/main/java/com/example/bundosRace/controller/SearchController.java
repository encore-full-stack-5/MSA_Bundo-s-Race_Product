package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.service.ProductsService;
import com.example.bundosRace.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("api/v1/search")
//@RequiredArgsConstructor
//public class SearchController {
//
//    private final SearchService searchService;
//
//    @PostMapping
//    public ResponseEntity<?> test() {
//        searchService.test();
//        return ResponseEntity.ok("success");
//    }
//}
