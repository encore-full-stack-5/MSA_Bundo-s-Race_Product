package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
}
