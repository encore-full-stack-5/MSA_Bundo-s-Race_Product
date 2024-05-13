package com.example.bundosRace.service;

import com.example.bundosRace.domain.Seller;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.response.BrandListResponse;
import com.example.bundosRace.repository.jpa.ProductsRepository;
import com.example.bundosRace.repository.jpa.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ProductsRepository productsRepository;

    @Override
    @Transactional
    public void createSeller(CreateSellerRequest createSellerRequest) {
        Seller seller = createSellerRequest.toEntity();
        sellerRepository.save(seller);
    }

    @Override
    public List<BrandListResponse> getAllBrand() {
        return sellerRepository.findAll().stream()
                .map((seller) -> new BrandListResponse(seller.getId(), seller.getName())).toList();
    }

    @Override
    public List<BrandListResponse> getAllBrandByCategory(long categoryId) {
        return productsRepository.findByCategoryId(categoryId).stream()
                .map(product ->
                        new BrandListResponse(product.getSeller().getId(), product.getSeller().getName()))
                .distinct()
                .toList();
    }

}
