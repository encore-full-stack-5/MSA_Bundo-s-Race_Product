package com.example.bundosRace.repository.elastic;

import com.example.bundosRace.domain.ProductForElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductForElastic, Long> {
    List<ProductForElastic> findByNameOrDescription(String name, String description);
    List<ProductForElastic> findByName(String name);
    ProductForElastic findById(long id);
}