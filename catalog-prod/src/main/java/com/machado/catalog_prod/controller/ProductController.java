package com.machado.catalog_prod.controller;

import com.machado.catalog_prod.entity.Product;
import com.machado.catalog_prod.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product) {
        this.productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product saved");
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Product> readProduct(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/read/all")
    public ResponseEntity<List<Product>> readAllProduct() {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        if (productService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.update(product, id);
        return ResponseEntity.status(HttpStatus.OK).body("Product updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (this.productService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }
}
