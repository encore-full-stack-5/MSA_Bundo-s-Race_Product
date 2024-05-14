package com.example.bundosRace.dto.response;

import com.example.bundosRace.domain.BlogDocument;
import com.example.bundosRace.domain.ProductForElastic;

import java.util.List;

public record TotalSearchResponse(
        List<ProductForElastic> products,
        List<BlogDocument> blogs
) {

}
