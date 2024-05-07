package com.example.bundosRace.service;

import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;

public interface ProductsService {

    // get
    ProductDetailResponse getProductDetail(Long productId);


    // create
    void createProduct(CreateProductRequest createProductRequest);
    void createProductOptionGroup(Long productId, Long optionGroupId);
    void createProductOption(Long productId, Long optionGroupId, Long optionId);


    // delete
    void deleteProduct(Long productId);


    // update
    void updateProduct(Long productId, UpdateProductRequest updateProductRequest);

}
