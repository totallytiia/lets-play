package com.userproductmanagement.letsplay.service.impl;

import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.repository.ProductRepository;
import com.userproductmanagement.letsplay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            // Product with the given id found, return it
            return optionalProduct.get();
        } else {
            // Product with the given id not found, you can handle this case as needed
            return null;
        }
    }

    // get one product

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    q

    // check if null

    @Override
    public Product updateProduct(String id, Product product) {
        Product productVar = productRepository.findById(id).get();
        productVar.setName(product.getName());
        productVar.setDescription(product.getDescription());
        productVar.setPrice(product.getPrice());
        return productRepository.save(productVar);
    }
}
