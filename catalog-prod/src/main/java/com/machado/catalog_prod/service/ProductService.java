package com.machado.catalog_prod.service;

import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void create(@Valid Product product) {
        Category category = categoryRepository.findByName(product.getCategory().getName())
                .orElse(product.getCategory());
        product.setCategory(category);
        productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByPrice(Double price) {
        return productRepository.findByPrice(price);
    }

    public List<Product> findByCategory(Long idCategory) {
        Category cat = new Category();
        cat.setId(idCategory);
        return productRepository.findByCategory(cat);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void update(@Valid Product product, Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        product.setId(id);
        productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }
}
