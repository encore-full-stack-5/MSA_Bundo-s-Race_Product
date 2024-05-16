package com.example.bundosRace.core.kafka;

import com.example.bundosRace.dto.request.BlogRequest;
import com.example.bundosRace.dto.request.Cafe;
import com.example.bundosRace.dto.request.LandRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.service.ProductsService;
import com.example.bundosRace.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer {

    @Autowired
    private SearchService searchService;
    @Autowired
    private ProductsService productsService;

    @KafkaListener(topics = "blog-topic")
    public void subscribeBlog(KafkaResponse<List<BlogRequest>> blogs) {
        searchService.insertBlogData(blogs.data());
    }

    @KafkaListener(topics = "land-topic")
    public void subscribeHouse(KafkaResponse<List<LandRequest>> lands) {
        System.out.println(lands.data().size());
        lands.data().forEach(blogRequest -> System.out.println(blogRequest.toString())
        );
        searchService.insertLandData(lands.data());
    }

    @KafkaListener(topics = "product-review-topic")
    public void subscribeProductReview(ReviewCountMessage data) {
        System.out.println(data.productId());
        productsService.updateReviewCount(data.productId());
    }

    @KafkaListener(topics = "cafe-topic")
    public void subscribeCage(KafkaResponse<List<Cafe>> blogs) {
    }
}