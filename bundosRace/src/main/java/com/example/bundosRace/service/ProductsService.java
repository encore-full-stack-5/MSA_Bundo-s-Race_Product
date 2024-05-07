package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.*;
import org.springframework.transaction.annotation.Transactional;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.dto.response.ProductListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    // get
    Product getProductDetail(Long productId);
    List<Category> getCategories();

    // create
    void createProduct(CreateProductRequest createProductRequest);
    void createProductOptionGroup(Long productId, CreateOptionGroupRequest createOptionGroupRequest);
    void createProductOption(Long optionGroupId, CreateOptionRequest CreateOptionRequest);
    void createSeller(CreateSellerRequest createSellerRequest);

    // delete
    void deleteProduct(Long productId);

    // update
    void updateProduct(Long productId, UpdateProductRequest updateProductRequest);

    Page<ProductListResponse> getProductListByCategoryAndSort(String category, PageRequest pageRequest);

}
