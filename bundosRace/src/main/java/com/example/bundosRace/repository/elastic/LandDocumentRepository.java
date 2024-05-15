package com.example.bundosRace.repository.elastic;

import com.example.bundosRace.domain.LandDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandDocumentRepository extends ElasticsearchRepository<LandDocument, Long> {

    List<LandDocument> findByNameOrDescription(String name, String description);
}
