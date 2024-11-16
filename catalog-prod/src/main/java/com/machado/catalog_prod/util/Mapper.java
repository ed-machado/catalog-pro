package com.machado.catalog_prod.util;

import com.machado.catalog_prod.dto.CategoryDTO;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.dto.ProductDTO;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;

public class Mapper {

    public static ProductDTO toProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        if (product.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());
            categoryDTO.setDescription(product.getCategory().getDescription());
            productDTO.setCategory(categoryDTO);
        }

        return productDTO;
    }

    public static Product toProduct(ProductRequest productRequest, Category category) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);
        return product;
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public static Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return category;
    }
}
