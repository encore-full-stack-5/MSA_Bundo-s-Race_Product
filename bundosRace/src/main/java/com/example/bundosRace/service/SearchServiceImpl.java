package com.example.bundosRace.service;

import com.example.bundosRace.core.kafka.KafkaResponse;
import com.example.bundosRace.core.kafka.PostProducer;
import com.example.bundosRace.domain.BaseElasticData;
import com.example.bundosRace.domain.BlogDocument;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.domain.ProductForElastic;
import com.example.bundosRace.dto.request.BlogRequest;
import com.example.bundosRace.dto.response.TotalSearchResponse;
import com.example.bundosRace.repository.elastic.BlogDocumentRepository;
import com.example.bundosRace.repository.elastic.ProductDocumentRepository;
import com.example.bundosRace.repository.jpa.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductDocumentRepository productDocumentRepository;
    private final BlogDocumentRepository blogDocumentRepository;
    private final ProductsRepository productsRepository;
    private final PostProducer producer;

    public void delete() {
        elasticsearchOperations.indexOps(IndexCoordinates.of("products")).delete();
    }
    @Transactional
    public void init() {
//        delete();
//        createIndexWithNori();
        insertProductData();
    }

    @Override
    public KafkaResponse<String> sendKafkaMessage() {
        KafkaResponse<String> kafkaResponse = new KafkaResponse<>("GET", "TEST");
        producer.send(kafkaResponse, "status");
        return kafkaResponse;
    }

    private void insertProductData() {
        List<ProductForElastic> productsForElastic = productsRepository.findAll().stream()
                .map(Product::toProductForElastic)
                .toList();
        productDocumentRepository.saveAll(productsForElastic);
    }

    @Override
    public void insertBlogData(List<BlogRequest> blogs) {
        List<BlogDocument> blogDocuments = blogs.stream().map(BlogRequest::toDocument).toList();
        blogDocumentRepository.saveAll(blogDocuments);
    }

    @Override
    public List<ProductForElastic> searchProduct(String keyword) {
        return productDocumentRepository.findByNameOrDescription(keyword, keyword);
    }

    @Override
    public List<BlogDocument> searchBlog(String keyword) {
        return blogDocumentRepository.findByNameOrDescription(keyword, keyword);
    }

    @Override
    @Transactional
    public TotalSearchResponse totalSearch(String keyword) {
        List<ProductForElastic> products = searchProduct(keyword);
        List<BlogDocument> blogs = searchBlog(keyword);
        return new TotalSearchResponse(products, blogs);
    }

    @Override
    public ProductForElastic findById(long id) {
        return productDocumentRepository.findById(id);
    }



    @Transactional
    @Override
    public void test() {
        init();
    }
}
