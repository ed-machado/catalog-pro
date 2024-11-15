package com.machado.catalog_prod.service;

import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldCreateProductWithNewCategory() {
        ProductRequest request = new ProductRequest("Cup", "Plastic cup", 1.50,
                new CategoryRequest("Kitchenware", "Utensils"));

        productService.create(request);

        Product savedProduct = productRepository.findAll().get(0);
        Category savedCategory = categoryRepository.findAll().get(0);

        assertEquals("Cup", savedProduct.getName());
        assertEquals("Kitchenware", savedCategory.getName());
    }

    @Test
    void shouldSoftDeleteProduct() {
        Category category = categoryRepository.save(new Category(null, "Kitchenware",
                "Category Description", null));
        Product product = productRepository.save(new Product(null, "Plate", "Description",
                2.99, category));

        productService.delete(product.getId());

        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        assertTrue(deletedProduct.isEmpty());
    }

    @Test
    void shouldFindProductsByCategory() {
        Category category = categoryRepository.save(new Category(null, "Kitchenware",
                "Utensils", null));
        productRepository.save(new Product(null, "Plate", "Description",
                2.99, category));

        var products = productService.findByCategory("Kitchenware");

        assertEquals(1, products.size());
        assertEquals("Plate", products.get(0).getName());
    }

    @Test
    void shouldUpdateProduct() {
        Category category = categoryRepository.save(new Category(null,
                "Kitchenware", "Utensils", null));
        Product product = productRepository.save(new Product(null, "Cup",
                "Plastic cup", 1.50, category));

        ProductRequest updateRequest = new ProductRequest("Glass", "Glass cup",
                2.50, new CategoryRequest("Kitchenware", "Utensils"));

        productService.update(product.getId(), updateRequest);

        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals("Glass", updatedProduct.getName());
        assertEquals(2.50, updatedProduct.getPrice());
    }
}
