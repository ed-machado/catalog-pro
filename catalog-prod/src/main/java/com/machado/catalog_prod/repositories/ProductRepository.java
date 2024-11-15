package com.machado.catalog_prod.repositories;

import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByName(String name);
    public List<Product> findByCategory(Category category);

    @Query("FROM Product p WHERE p.price <= :price")
    public List<Product> findByPrice(Double price);

}
