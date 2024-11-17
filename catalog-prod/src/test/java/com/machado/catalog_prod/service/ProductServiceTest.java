package com.machado.catalog_prod.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.machado.catalog_prod.dto.ProductDTO;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.dto.CategoryRequest;
import com.machado.catalog_prod.entity.Category;
import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.exception.CategoryNotFoundException;
import com.machado.catalog_prod.exception.ProductNotFoundException;
import com.machado.catalog_prod.repositories.CategoryRepository;
import com.machado.catalog_prod.repositories.ProductRepository;
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

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

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
    void testCreateProduct() {
        ProductRequest productRequest = new ProductRequest("Product1", "Description1", 100.0, new CategoryRequest("Category1", "Description1"));
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Product product = new Product(1L, "Product1", "Description1", 100.0, category);

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.create(productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testFindById() {
        Long productId = 1L;
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Product product = new Product(productId, "Product1", "Description1", 100.0, category);
        ProductDTO productDTO = Mapper.toProductDTO(product);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDTO result = productService.findById(productId);

        assertEquals(productDTO, result);
    }

    @Test
    void testFindById_ProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(productId));
    }

    @Test
    void testFindByName() {
        String name = "Product1";
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Product product = new Product(1L, name, "Description1", 100.0, category);
        List<Product> products = List.of(product);
        List<ProductDTO> productDTOs = products.stream().map(Mapper::toProductDTO).toList();

        when(productRepository.findByName(name)).thenReturn(products);

        List<ProductDTO> result = productService.findByName(name);

        assertEquals(productDTOs, result);
    }

    @Test
    void testFindByPrice() {
        Double price = 100.0;
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Product product = new Product(1L, "Product1", "Description1", price, category);
        List<Product> products = List.of(product);
        List<ProductDTO> productDTOs = products.stream().map(Mapper::toProductDTO).toList();

        when(productRepository.findByPrice(price)).thenReturn(products);

        List<ProductDTO> result = productService.findByPrice(price);

        assertEquals(productDTOs, result);
    }

    @Test
    void testFindByCategory() {
        String categoryName = "Category1";
        Category category = new Category();
        category.setName(categoryName);
        category.setDescription("Description1");
        Product product = new Product(1L, "Product1", "Description1", 100.0, category);
        List<Product> products = List.of(product);
        List<ProductDTO> productDTOs = products.stream().map(Mapper::toProductDTO).toList();

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(products);

        List<ProductDTO> result = productService.findByCategory(categoryName);

        assertEquals(productDTOs, result);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        category.setName("Category1");
        category.setDescription("Description1");
        Product product = new Product(1L, "Product1", "Description1", 100.0, category);
        Page<Product> products = new PageImpl<>(List.of(product));
        Page<ProductDTO> productDTOs = products.map(Mapper::toProductDTO);

        when(productRepository.findAll(pageable)).thenReturn(products);

        Page<ProductDTO> result = productService.findAll(pageable);

        assertEquals(productDTOs, result);
    }

    @Test
    void testUpdate() {
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("UpdatedProduct", "UpdatedDescription", 150.0, new CategoryRequest("UpdatedCategory", "UpdatedDescription"));
        Category category = new Category();
        category.setName("UpdatedCategory");
        category.setDescription("UpdatedDescription");
        Product product = new Product(productId, "Product1", "Description1", 100.0, category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.update(productId, productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDelete() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testCreateProductWithNewCategory() {
        ProductRequest productRequest = new ProductRequest("Product1", "Description1", 100.0, new CategoryRequest("NewCategory", "NewDescription"));
        Category newCategory = new Category();
        newCategory.setName("NewCategory");
        newCategory.setDescription("NewDescription");
        Product product = new Product(1L, "Product1", "Description1", 100.0, newCategory);

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.create(productRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("UpdatedProduct", "UpdatedDescription", 150.0, new CategoryRequest("UpdatedCategory", "UpdatedDescription"));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(productId, productRequest));
    }

    @Test
    void testDeleteProductNotFound() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));
    }

    @Test
    void testFindByCategoryNotFound() {
        String categoryName = "NonExistentCategory";

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.findByCategory(categoryName));
    }
}