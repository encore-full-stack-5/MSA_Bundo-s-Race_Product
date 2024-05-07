package com.example.bundosRace.service;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import com.example.bundosRace.dto.request.CreateProductRequest;
import com.example.bundosRace.repository.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductsServiceImplTest {
    @Autowired
    private ProductsService productsService;

    @Test
    void getProductDetail() {
    }

    @Test
    void getCategories() {
        List<Category> categories = productsService.getCategories();
        categories.forEach((category) -> {
            System.out.println("category = " + category.getName() + " id = " + category.getId());
        });
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isGreaterThan(0);
    }

    @Test
    public void createProduct() {
        productsService.createProduct(1L, new CreateProductRequest(
                1L,
                1L,
                "Test Product",
                "test.jpg",
                10000,
                2500,
                100,
                1
        ));
    }

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