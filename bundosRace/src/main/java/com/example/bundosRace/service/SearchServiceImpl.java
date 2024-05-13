package com.example.bundosRace.service;

import com.example.bundosRace.domain.Product;
import com.example.bundosRace.domain.ProductForElastic;
import com.example.bundosRace.repository.elastic.ProductDocumentRepository;
import com.example.bundosRace.repository.jpa.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private ProductDocumentRepository productDocumentRepository;
    @Autowired
    private ProductsRepository productsRepository;

    public void delete() {
        elasticsearchOperations.indexOps(IndexCoordinates.of("products")).delete();
    }

    @Transactional
    public void init() {
//        delete();
//        createIndexWithNori();
        insertSampleData();
    }

    private void createIndexWithNori() {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("products");

        // 인덱스 존재 여부 확인
        boolean indexExists = elasticsearchOperations.indexOps(indexCoordinates).exists();

        Map<String, Object> settings = Map.of(
                "index", Map.of(
                        "analysis", Map.of(
                                "analyzer", Map.of(
                                        "korean", Map.of(
                                                "type", "custom",
                                                "tokenizer", "nori_tokenizer",
                                                "filter", List.of("nori_readingform", "lowercase", "nori_part_of_speech")
                                        )
                                )
                        )
                )
        );

        Map<String, Object> mapping = Map.of(
                "properties", Map.of(
                        "name", Map.of(
                                "type", "text",
                                "analyzer", "korean"
                        ),
                        "description", Map.of(
                                "type", "text",
                                "analyzer", "korean"
                        )
                )
        );

        elasticsearchOperations.indexOps(indexCoordinates).create(settings);
        elasticsearchOperations.indexOps(indexCoordinates).putMapping(ProductForElastic.class);
    }

    private void insertSampleData() {
        List<ProductForElastic> productsForElastic = productsRepository.findAll().stream()
                .map(Product::toProductForElastic)
                .toList();

        productsForElastic.forEach((product) -> {
                    System.out.println(product.getName());
        });

        productDocumentRepository.saveAll(productsForElastic);
    }

    @Override
    public List<ProductForElastic> search(String keyword) {
        return productDocumentRepository.findByNameOrDescription(keyword, keyword);
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
