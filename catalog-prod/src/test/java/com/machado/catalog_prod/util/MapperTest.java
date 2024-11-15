package com.machado.catalog_prod.util;

import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    @Test
    void shouldMapProductRequestToProduct() {
        Category category = new Category(1L, "Kitchenware", "Description", null);
        ProductRequest request = new ProductRequest("Plate", "Ceramic plate", 12.99, null);

        Product product = Mapper.toProduct(request, category);

        assertEquals("Plate", product.getName());
        assertEquals("Kitchenware", product.getCategory().getName());
    }
}
