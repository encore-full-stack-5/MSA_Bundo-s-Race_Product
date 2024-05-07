package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductsServiceImplTest {
    @Autowired
    private ProductsService productsService;

    @Test
    void getProductDetail() {
    }

    @Test
    void getCategories() {
        List<Category> categories = productsService.getCategories();
        System.out.println("categories = " + categories);
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isGreaterThan(0);

    }

//    @Test
//    @Rollback // 롤백을 명시적으로 활성화 (기본값이므로 생략 가능)
//    public void createProduct() {
//        // make dummy CreateProductRequest
//        CreateProductRequest createProductRequest =
//                new CreateProductRequest(
//                        1L,
//                        1L,
//                        "Test Product",
//                        "test.jpg",
//                        10000,
//                        2500,
//                        100,
//                        1
//                );
//
//
//        Product product = createProductRequest.toEntity();
//        Product savedProduct = productsRepository.save(product);
//
//        assertThat(savedProduct).isNotNull();
//        assertThat(savedProduct.getId()).isNotNull();
//        assertThat(savedProduct.getName()).isEqualTo("Test Product");
//    }

    @Test
    void createProductOptionGroup() {
    }

    @Test
    void createProductOption() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }
}