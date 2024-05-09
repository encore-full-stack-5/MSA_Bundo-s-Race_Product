package com.example.bundosRace.service;

import com.example.bundosRace.core.error.ExpectedError;
import com.example.bundosRace.core.error.UnexpectedError;
import com.example.bundosRace.domain.*;
import com.example.bundosRace.dto.request.*;
import com.example.bundosRace.dto.response.ProductListResponse;
import com.example.bundosRace.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final ProductListCustom productListCustom;

    @Override
    @Transactional
    public Product getProductDetail(Long productId) {
        return productsRepository.findById(productId).orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public void createProduct(CreateProductRequest request) {
        Product product = request.toEntity();
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        Seller seller = sellerRepository.findById(request.sellerId()).orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 판매자가 존재하지 않습니다."));
        product.setCategory(category);
        product.setSeller(seller);
        addOptionGroupsInProduct(request, product); // 옵션 그룹과 옵션을 순차적으로 생성후 각각 상품과 옵셥그룹에 add
        productsRepository.save(product);
    }


    @Override
    @Transactional
    public void createProductOptionGroup(Long productId, CreateOptionGroupRequest createOptionGroupRequest) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        OptionGroup optionGroup = createOptionGroupRequest.toEntity();
        addOptionsInOptionGroup(createOptionGroupRequest, optionGroup);
        product.addOptionGroup(optionGroup);
    }

    @Override
    @Transactional
    public void createProductOption(Long productId, Long optionGroupId, CreateOptionRequest CreateOptionRequest) {
        OptionGroup optionGroup = optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 옵션 그룹이 존재하지 않습니다."));
        optionGroup.addOption(CreateOptionRequest.toEntity());
    }

    @Override
    @Transactional
    public void createSeller(CreateSellerRequest createSellerRequest) {
        Seller seller = createSellerRequest.toEntity();
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        product.delete();
    }

    @Override
    @Transactional
    public void updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new UnexpectedError.IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        product.updateEntity(updateProductRequest);
    }

    @Override
    @Transactional
    public void sellProducts(SellProductsRequest sellProductsRequest) {
        sellProductsRequest.sellProducts().forEach((saleProduct) -> {
            Product product = productsRepository.findById(saleProduct.productId())
                    .orElseThrow(() -> new ExpectedError.ResourceNotFoundException("해당 "+ saleProduct.productId() +" 상품이 존재하지 않습니다."));
            product.sell(saleProduct.count(), saleProduct.optionIds());
        });
    }



    private void addOptionGroupsInProduct(CreateProductRequest request, Product product) {
        request.optionGroups().forEach((optionGroupRequest) -> {
            OptionGroup optionGroup = optionGroupRequest.toEntity();
            addOptionsInOptionGroup(optionGroupRequest, optionGroup);
            product.addOptionGroup(optionGroup);
        });
    }

    private void addOptionsInOptionGroup(CreateOptionGroupRequest optionGroupRequest, OptionGroup optionGroup) {
        optionGroupRequest.options().forEach((optionRequest) -> {
            Option option = optionRequest.toEntity();
            option.setOptionGroup(optionGroup);
            optionGroup.addOption(option);
        });
    }

    @Override
    public Page<ProductListResponse> getProductListByCategoryAndSort(Long categoryId, Integer startPrice, Integer endPrice ,Long sellerId, Pageable pageable) {
        //start랑 end가 start가 null인 경우 from값은 Integer
        int maxValue = 0;
        if(endPrice == 0) maxValue = Integer.MAX_VALUE;
        else maxValue = endPrice;

        //categoryId랑 sellerId가 0이 들어오면 타는 로직을 타야 함
        

        //sort로직


        Page<Product> productListPage = productListCustom.filterProductList(categoryId,startPrice,maxValue,sellerId,pageable);

        return productListPage.map(ProductListResponse :: fromEntity);
    }
}
