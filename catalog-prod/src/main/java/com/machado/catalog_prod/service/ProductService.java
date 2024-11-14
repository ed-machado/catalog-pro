package com.machado.catalog_prod.service;

import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void create(@Valid Product product) {
        productRepository.save(product);
    }
}
