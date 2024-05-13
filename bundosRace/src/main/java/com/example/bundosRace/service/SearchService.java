package com.example.bundosRace.service;

import com.example.bundosRace.domain.ProductForElastic;

import java.util.List;

public interface SearchService {
    void test();
    List<ProductForElastic> search(String keyword);
    ProductForElastic findById(long id);

}
