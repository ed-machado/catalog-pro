package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.dto.CategoryDTO;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.create(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CategoryDTO> readCategory(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/read/byName")
    public ResponseEntity<List<CategoryDTO>> readCategoryByName(@RequestParam String name) {
        List<CategoryDTO> categoryList = categoryService.findByName(name);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/read/all")
    public ResponseEntity<Page<CategoryDTO>> readAllCategory(Pageable pageable) {
        Page<CategoryDTO> categoryList = categoryService.findAll(pageable);
        return ResponseEntity.ok(categoryList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.update(id, categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully.");
    }
}
