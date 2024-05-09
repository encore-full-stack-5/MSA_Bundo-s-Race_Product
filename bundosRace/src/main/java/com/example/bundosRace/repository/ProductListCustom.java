package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductListCustom {
    Page<Product> filterProductList(Long categoryId, Integer startPrice, Integer endPrice, Long sellerId, Pageable pageable);
}
