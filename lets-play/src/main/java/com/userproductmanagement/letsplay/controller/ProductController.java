package com.userproductmanagement.letsplay.controller;

import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.service.ProductService;
import com.userproductmanagement.letsplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Boolean updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct) {
            return new ResponseEntity<>("Product was updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product was not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        Boolean product = productService.deleteProduct(id);
        if (product) {
            return new ResponseEntity<>("Product was deleted successfully", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Product was not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}

