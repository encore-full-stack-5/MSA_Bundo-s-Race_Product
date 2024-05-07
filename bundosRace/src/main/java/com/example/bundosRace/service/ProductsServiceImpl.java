package com.example.bundosRace.service;

import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {


    private final ProductsRepository productsRepository;

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        return null;
    }

    @Override
    public void createProduct(CreateProductRequest createProductRequest) {
        Product product = createProductRequest.toEntity();

    }

    @Override
    public void createProductOptionGroup(Long productId, Long optionGroupId) {

    }

    @Override
    public void createProductOption(Long productId, Long optionGroupId, Long optionId) {

    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public void updateProduct(Long productId, UpdateProductRequest updateProductRequest) {

    }
}
