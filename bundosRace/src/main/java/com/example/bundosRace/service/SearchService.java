package com.example.bundosRace.service;

import com.example.bundosRace.core.kafka.KafkaResponse;
import com.example.bundosRace.domain.BaseElasticData;
import com.example.bundosRace.domain.BlogDocument;
import com.example.bundosRace.domain.ProductForElastic;
import com.example.bundosRace.dto.request.BlogRequest;
import com.example.bundosRace.dto.request.LandRequest;
import com.example.bundosRace.dto.response.TotalSearchResponse;

import java.util.List;
import java.util.Map;

public interface SearchService {
    void insertBlogData(List<BlogRequest> blogs);
    void insertLandData(List<LandRequest> lands);
    List<ProductForElastic> searchProduct(String keyword);

    List<BlogDocument> searchBlog(String keyword);

    ProductForElastic findById(long id);

    TotalSearchResponse totalSearch(String keyword);

    KafkaResponse<String> sendKafkaMessage();
}
