package com.example.bundosRace.core.kafka;

import com.example.bundosRace.dto.request.BlogRequest;
import com.example.bundosRace.dto.request.Cafe;
import com.example.bundosRace.dto.request.House;
import com.example.bundosRace.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer {

    @Autowired
    private SearchService searchService;

    @KafkaListener(topics = "blog-topic")
    public void subscribeBlog(KafkaResponse<List<BlogRequest>> blogs) {
        System.out.println(blogs.data().size());
        blogs.data().forEach(blogRequest -> System.out.println(blogRequest.toString())
        );
        searchService.insertBlogData(blogs.data());
    }
    @KafkaListener(topics = "house-topic")
    public void subscribeHouse(KafkaResponse<List<House>> blogs) {
    }

    @KafkaListener(topics = "cafe-topic")
    public void subscribeCage(KafkaResponse<List<Cafe>> blogs) {
    }
}