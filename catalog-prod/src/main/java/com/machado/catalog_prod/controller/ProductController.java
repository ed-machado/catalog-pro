package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.dto.ProductDTO;
import com.machado.catalog_prod.dto.ProductRequest;
import com.machado.catalog_prod.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ProductDTO> readProduct(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/read/byName")
    public ResponseEntity<List<ProductDTO>> readProductByName(@RequestParam String name) {
        List<ProductDTO> productList = productService.findByName(name);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/read/byPrice")
    public ResponseEntity<List<ProductDTO>> readProductByPrice(@RequestParam Double price) {
        List<ProductDTO> productList = productService.findByPrice(price);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/read/byCategoryName")
    public ResponseEntity<List<ProductDTO>> readProductByCategoryName(@RequestParam String categoryName) {
        List<ProductDTO> productList = productService.findByCategory(categoryName);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/read/all")
    public ResponseEntity<Page<ProductDTO>> readAllProduct(Pageable pageable) {
        Page<ProductDTO> productList = productService.findAll(pageable);
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}

