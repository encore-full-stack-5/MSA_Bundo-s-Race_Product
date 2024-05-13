package com.example.bundosRace.service;

import com.example.bundosRace.domain.Seller;
import com.example.bundosRace.dto.request.CreateSellerRequest;
import com.example.bundosRace.dto.response.BrandListResponse;

import java.util.List;

public interface SellerService {

    void createSeller(CreateSellerRequest createSellerRequest);
    List<BrandListResponse> getAllBrand();
    List<BrandListResponse> getAllBrandByCategory(long categoryId);

}
