package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    
}
