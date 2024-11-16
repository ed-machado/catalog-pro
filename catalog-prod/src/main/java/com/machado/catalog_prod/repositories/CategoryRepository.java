package com.machado.catalog_prod.repositories;

import com.machado.catalog_prod.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
}
