package com.machado.catalog_prod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private String description;
}
