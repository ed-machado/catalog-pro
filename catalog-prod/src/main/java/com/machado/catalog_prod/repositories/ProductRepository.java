package com.machado.catalog_prod.repositories;

import com.machado.catalog_prod.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
