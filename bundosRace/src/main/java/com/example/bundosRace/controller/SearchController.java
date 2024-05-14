package com.example.bundosRace.controller;

import com.example.bundosRace.domain.BaseElasticData;
import com.example.bundosRace.domain.ProductForElastic;
import com.example.bundosRace.dto.response.TotalSearchResponse;
import com.example.bundosRace.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/total")
    public ResponseEntity<TotalSearchResponse> totalSearch(
            @RequestParam("keyword") String keyword
    ) {
        return ResponseEntity.ok(searchService.totalSearch(keyword));
    }

    @GetMapping("/test")
    public void testSend() {
        searchService.sendKafkaMessage();
    }

    @GetMapping
    public ResponseEntity<List<ProductForElastic>> search(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(searchService.searchProduct(keyword));
    }
}
