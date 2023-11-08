package com.userproductmanagement.letsplay.service.impl;

import com.userproductmanagement.letsplay.exceptions.EntityNotFoundException;
import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.repository.ProductRepository;
import com.userproductmanagement.letsplay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(String id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.deleteById(id);
                    return product;
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    // check if null

    public Product updateProduct(String id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPrice(productDetails.getPrice());
                    // Add any other fields that need to be updated here
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
}
