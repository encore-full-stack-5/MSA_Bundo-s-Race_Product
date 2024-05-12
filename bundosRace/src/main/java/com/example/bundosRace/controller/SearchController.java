package com.example.bundosRace.controller;

import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.service.ProductsService;
import com.example.bundosRace.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public ResponseEntity<?> test() {
        searchService.test();
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(searchService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(searchService.search(keyword));
    }
}
