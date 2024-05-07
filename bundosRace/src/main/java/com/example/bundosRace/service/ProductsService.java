package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    // get
    ProductDetailResponse getProductDetail(Long productId);
    List<Category> getCategories();


    // create
    void createProduct(Long id, CreateProductRequest createProductRequest);
    void createProductOptionGroup(Long productId, Long optionGroupId);
    void createProductOption(Long productId, Long optionGroupId, Long optionId);
    void createSeller(CreateSellerRequest createSellerRequest);


    // delete
    void deleteProduct(Long productId);


    // update
    void updateProduct(Long productId, UpdateProductRequest updateProductRequest);

}
