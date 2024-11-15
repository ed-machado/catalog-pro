package com.machado.catalog_prod.service;

import com.machado.catalog_prod.dto.ProductDTO;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.exception.CategoryNotFoundException;
import com.machado.catalog_prod.exception.ProductNotFoundException;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.repositories.ProductRepository;
import com.machado.catalog_prod.util.Mapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void create(ProductRequest productRequest) {
        log.info("Creating product: {}", productRequest);

        Category category = categoryRepository.findByName(productRequest.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(productRequest.getCategory().getName());
                    newCategory.setDescription(productRequest.getCategory().getDescription());
                    return categoryRepository.save(newCategory);
                });

        Product product = Mapper.toProduct(productRequest, category);
        productRepository.save(product);

        log.info("Product created successfully: {}", product.getId());
    }

    public ProductDTO findById(Long id) {
        log.info("Finding product by ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        return Mapper.toProductDTO(product);
    }

    public List<ProductDTO> findByName(String name) {
        log.info("Finding products by name: {}", name);
        return productRepository.findByName(name).stream()
                .map(Mapper::toProductDTO)
                .toList();
    }

    public List<ProductDTO> findByPrice(Double price) {
        log.info("Finding products by price: {}", price);
        return productRepository.findByPrice(price).stream()
                .map(Mapper::toProductDTO)
                .toList();
    }

    public List<ProductDTO> findByCategory(String categoryName) {
        log.info("Finding products by category name: {}", categoryName);

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + categoryName));

        return productRepository.findByCategory(category).stream()
                .map(Mapper::toProductDTO)
                .toList();
    }


    public Page<ProductDTO> findAll(Pageable pageable) {
        log.info("Finding all products with pagination: {}", pageable);
        return productRepository.findAll(pageable)
                .map(Mapper::toProductDTO);
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        log.info("Updating product ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findByName(productRequest.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(productRequest.getCategory().getName());
                    newCategory.setDescription(productRequest.getCategory().getDescription());
                    return categoryRepository.save(newCategory);
                });

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        productRepository.save(product);
        log.info("Product updated successfully: {}", id);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting product ID: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted successfully: {}", id);
    }
}
