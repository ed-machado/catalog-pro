package com.machado.catalog_prod.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.machado.catalog_prod.dto.CategoryDTO;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.exception.CategoryNotFoundException;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.util.Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("Category1", "Description1");
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.create(categoryRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testFindById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.findById(categoryId);

        assertEquals(Mapper.toCategoryDTO(category), result);
    }

    @Test
    void testFindById_CategoryNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(categoryId));
    }

    @Test
    void testFindByName() {
        String name = "Category1";
        Category category = new Category();
        category.setName(name);
        category.setDescription("Description1");
        List<Category> categories = List.of(category);

        when(categoryRepository.findByNameContainingIgnoreCase(name)).thenReturn(categories);

        List<CategoryDTO> result = categoryService.findByName(name);

        assertEquals(categories.stream().map(Mapper::toCategoryDTO).toList(), result);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Page<Category> categories = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categories);

        Page<CategoryDTO> result = categoryService.findAll(pageable);

        assertEquals(categories.map(Mapper::toCategoryDTO), result);
    }

    @Test
    void testUpdate() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("UpdatedCategory", "UpdatedDescription");
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.update(categoryId, categoryRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate_CategoryNotFound() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("UpdatedCategory", "UpdatedDescription");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(categoryId, categoryRequest));
    }

    @Test
    void testDelete() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDelete_CategoryNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(categoryId));
    }

    @Test
    void testFindByName_CategoryNotFound() {
        String name = "NonExistentCategory";

        when(categoryRepository.findByNameContainingIgnoreCase(name)).thenReturn(List.of());

        List<CategoryDTO> result = categoryService.findByName(name);

        assertTrue(result.isEmpty());
    }
}