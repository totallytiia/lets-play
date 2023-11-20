package com.userproductmanagement.letsplay.service.impl;

import com.userproductmanagement.letsplay.exception.EntityNotFoundException;
import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.repository.ProductRepository;
import com.userproductmanagement.letsplay.repository.UserRepository;
import com.userproductmanagement.letsplay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

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
    public Product addProduct(UserDetails userDetails, Product product) {

        if ( userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found in database."));
        product.setUserId(user.getId());

        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProduct(String id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }


    public Boolean updateProduct(String id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPrice(productDetails.getPrice());
                    productRepository.save(existingProduct);
                    return true;
                })
                .orElse(false);
    }
}
