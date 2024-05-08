package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Category;
import com.example.bundosRace.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

}
