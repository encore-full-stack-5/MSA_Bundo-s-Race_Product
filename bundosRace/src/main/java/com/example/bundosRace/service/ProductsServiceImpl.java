package com.example.bundosRace.service;

import com.example.bundosRace.core.UnexpectedError;
import com.example.bundosRace.domain.*;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.request.UpdateProductRequest;
import com.example.bundosRace.dto.response.ProductDetailResponse;
import com.example.bundosRace.repository.CategoryRepository;
import com.example.bundosRace.repository.ProductsRepository;
import com.example.bundosRace.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {


    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public void createProduct(Long id, CreateProductRequest request) {
        Product product = request.toEntity();
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        Seller seller = sellerRepository.findById(request.sellerId()).orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 판매자가 존재하지 않습니다."));
        List<OptionGroup> optionGroups = request.optionGroups().stream().map((optionGroupRequest) -> {
            OptionGroup optionGroup = optionGroupRequest.toEntity();
            List<Option> options = optionGroupRequest.options().stream().map((optionRequest) -> {
                Option option = optionRequest.toEntity();
                option.setOptionGroup(optionGroup);
                return option;
            }).toList();

            optionGroup.setOptions(options);
            optionGroup.setProduct(product);
            return optionGroup;
        }).toList();

        product.setCategory(category);
        product.setSeller(seller);
        product.setOptionGroups(optionGroups);

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
