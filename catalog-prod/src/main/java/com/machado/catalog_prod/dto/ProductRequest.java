package com.machado.catalog_prod.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

    @NotNull
    @Size(max = 255)
    private String name;

    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private CategoryRequest category;
}
