package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.*;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsService {

    // get
    Product getProductDetail(Long productId);
    List<Category> getCategories();

    // create
    void createProduct(CreateProductRequest createProductRequest);
    void createProductOptionGroup(Long productId, CreateOptionGroupRequest createOptionGroupRequest);
    void createProductOption(Long productId, Long optionGroupId, CreateOptionRequest CreateOptionRequest);
    void createSeller(CreateSellerRequest createSellerRequest);

    // delete
    void deleteProduct(Long productId);

    // update
    void updateProduct(Long productId, UpdateProductRequest updateProductRequest);
    void sellProducts(SellProductsRequest sellProductsRequest);

    Page<ProductListResponse> getProductListByCategoryAndSort(
            Long category, Integer startPrice,Integer endPrice,Long sellerId,Pageable pageable);
    void validateProduct(Long productId, ValidateProductRequest validateProductRequest);
}
