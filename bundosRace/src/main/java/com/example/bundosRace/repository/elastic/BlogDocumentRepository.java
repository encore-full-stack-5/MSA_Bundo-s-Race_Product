package com.example.bundosRace.repository.elastic;

import com.example.bundosRace.domain.BaseElasticData;
import com.example.bundosRace.domain.BlogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogDocumentRepository extends ElasticsearchRepository<BlogDocument, Long> {

    List<BlogDocument> findByNameOrDescription(String name, String description);
}
