package com.example.bundosRace.service;

import com.example.bundosRace.core.util.LogUtil;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.repository.ProductsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//@Service
//public class SearchServiceImpl implements SearchService {
//
//    @Autowired
//    ElasticsearchOperations operations;
//    @Autowired
//    ElasticRepository repository;
//    @Autowired
//    ProductsRepository productsRepository;
//
//    @PreDestroy
//    public void deleteIndex() {
//        operations.indexOps(ProductForElastic.class).delete();
//    }
//
//    @PostConstruct
//    public void insertDataSample() {
//        operations.indexOps(ProductForElastic.class).refresh();
//        List<ProductForElastic> products = productsRepository.findAll().stream().map(Product::toProductForElastic).toList();
////        LogUtil.logPretty(products.get(0).toString());
//
//        repository.saveAll(products);
//    }
//
//    @Override
//    public void test() {
//        insertDataSample();
//    }
//}
