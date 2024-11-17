package com.machado.catalog_prod.util;

import com.machado.catalog_prod.dto.CategoryDTO;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.dto.ProductDTO;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void testToProductDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic items");

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("A powerful laptop");
        product.setPrice(1000.0);
        product.setCategory(category);

        ProductDTO productDTO = Mapper.toProductDTO(product);

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertNotNull(productDTO.getCategory());
        assertEquals(category.getId(), productDTO.getCategory().getId());
        assertEquals(category.getName(), productDTO.getCategory().getName());
        assertEquals(category.getDescription(), productDTO.getCategory().getDescription());
    }

    @Test
    void testToProduct() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic items");

        ProductRequest productRequest = new ProductRequest("Laptop", "A powerful laptop", 1000.0, new CategoryRequest("Electronics", "Electronic items"));

        Product product = Mapper.toProduct(productRequest, category);

        assertEquals(productRequest.getName(), product.getName());
        assertEquals(productRequest.getDescription(), product.getDescription());
        assertEquals(productRequest.getPrice(), product.getPrice());
        assertEquals(category, product.getCategory());
    }

    @Test
    void testToCategoryDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic items");

        CategoryDTO categoryDTO = Mapper.toCategoryDTO(category);

        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
        assertEquals(category.getDescription(), categoryDTO.getDescription());
    }

    @Test
    void testToCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("Electronics", "Electronic items");

        Category category = Mapper.toCategory(categoryRequest);

        assertEquals(categoryRequest.getName(), category.getName());
        assertEquals(categoryRequest.getDescription(), category.getDescription());
    }

    @Test
    void testToProductDTO_NullCategory() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("A powerful laptop");
        product.setPrice(1000.0);
        product.setCategory(null);

        ProductDTO productDTO = Mapper.toProductDTO(product);

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertNull(productDTO.getCategory());
    }

    @Test
    void testToProduct_NullCategory() {
        ProductRequest productRequest = new ProductRequest("Laptop", "A powerful laptop", 1000.0, null);

        Product product = Mapper.toProduct(productRequest, null);

        assertEquals(productRequest.getName(), product.getName());
        assertEquals(productRequest.getDescription(), product.getDescription());
        assertEquals(productRequest.getPrice(), product.getPrice());
        assertNull(product.getCategory());
    }
}