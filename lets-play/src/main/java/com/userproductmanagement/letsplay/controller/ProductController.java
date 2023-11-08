package com.userproductmanagement.letsplay.controller;

import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //check if person is authorized

    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PostMapping("/insert")
    public Product insert(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update/{id}")
    public Product update(@RequestBody Product product, @PathVariable String id) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public Product delete(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}

