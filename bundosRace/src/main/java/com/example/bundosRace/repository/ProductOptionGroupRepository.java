package com.example.bundosRace.repository;

import com.example.bundosRace.domain.Products_OptionGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionGroupRepository extends JpaRepository<Products_OptionGroups, Long> {
}
