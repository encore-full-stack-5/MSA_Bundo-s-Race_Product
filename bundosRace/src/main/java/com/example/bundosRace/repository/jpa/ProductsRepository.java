package com.example.bundosRace.repository.jpa;

import com.example.bundosRace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

}
