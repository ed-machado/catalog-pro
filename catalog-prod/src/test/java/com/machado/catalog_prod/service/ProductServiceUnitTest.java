package com.machado.catalog_prod.service;

import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.exception.CategoryNotFoundException;
import com.machado.catalog_prod.exception.ProductNotFoundException;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductWithExistingCategory() {
        Category category = new Category(null, "Kitchenware", "Category Description", null);

        when(categoryRepository.findByName("Kitchenware")).thenReturn(Optional.of(category));

        ProductRequest productRequest = new ProductRequest("Plate", "Description", 9.99,
                new CategoryRequest("Kitchenware", "Category Description"));

        productService.create(productRequest);

        verify(categoryRepository).findByName("Kitchenware");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(1L));

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldUpdateProduct() {
        Category category = new Category(1L, "Kitchenware", "Description", null);
        Product product = new Product(1L, "Cup", "Plastic cup", 1.50, category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findByName("Kitchenware")).thenReturn(Optional.of(category));

        ProductRequest updateRequest = new ProductRequest("Glass", "Glass cup", 2.50,
                new CategoryRequest("Kitchenware", "Description"));

        productService.update(1L, updateRequest);

        verify(productRepository).save(any(Product.class));
        assertEquals("Glass", product.getName());
        assertEquals(2.50, product.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFoundOnFindByCategory() {
        when(categoryRepository.findByName("Nonexistent Category")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.findByCategory("Nonexistent Category"));

        verify(categoryRepository).findByName("Nonexistent Category");
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentProduct() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.delete(1L));

        verify(productRepository).existsById(1L);
        verifyNoMoreInteractions(productRepository);
    }
}
