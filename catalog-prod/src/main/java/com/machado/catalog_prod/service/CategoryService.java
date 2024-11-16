package com.machado.catalog_prod.service;

import com.machado.catalog_prod.dto.CategoryDTO;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.exception.CategoryNotFoundException;
import com.machado.catalog_prod.repositories.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(@Valid CategoryRequest categoryRequest) {
        log.info("Creating category: {}", categoryRequest);
        Category category = Mapper.toCategory(categoryRequest);
        categoryRepository.save(category);
        log.info("Category created successfully: {}", category.getId());
    }

    public CategoryDTO findById(Long id) {
        log.info("Finding category by ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return Mapper.toCategoryDTO(category);
    }

    public List<CategoryDTO> findByName(String name) {
        log.info("Finding categories by name: {}", name);
        return categoryRepository.findByNameContainingIgnoreCase(name).stream()
                .map(Mapper::toCategoryDTO)
                .toList();
    }

    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.info("Finding all categories with pagination: {}", pageable);
        return categoryRepository.findAll(pageable)
                .map(Mapper::toCategoryDTO);
    }

    @Transactional
    public void update(Long id, @Valid CategoryRequest categoryRequest) {
        log.info("Updating category ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);
        log.info("Category updated successfully: {}", id);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting category ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
        log.info("Category deleted successfully: {}", id);
    }
}
