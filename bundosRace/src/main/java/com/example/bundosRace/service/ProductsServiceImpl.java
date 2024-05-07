package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.repository.CategoryRepository;
import com.example.bundosRace.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {


    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createProduct(Long id, CreateProductRequest createProductRequest) {
        Product product = createProductRequest.toEntity();
        System.out.println("product = " + product.toString());
    }

    @Override
    public void createProductOptionGroup(Long productId, Long optionGroupId) {
    }

    @Override
    public void createProductOption(Long productId, Long optionGroupId, Long optionId) {

    }

    @Override
    public void createSeller(CreateSellerRequest createSellerRequest) {

    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public void updateProduct(Long productId, UpdateProductRequest updateProductRequest) {

    }
}
