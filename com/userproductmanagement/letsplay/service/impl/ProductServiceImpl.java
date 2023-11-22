package com.userproductmanagement.letsplay.service.impl;

import com.userproductmanagement.letsplay.exception.EntityNotFoundException;
import com.userproductmanagement.letsplay.model.Product;
import com.userproductmanagement.letsplay.model.Role;
import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.repository.ProductRepository;
import com.userproductmanagement.letsplay.repository.UserRepository;
import com.userproductmanagement.letsplay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> getProductsByUserId(String userId) {
        return productRepository.findByUserId(userId);
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


    @Override
    public Boolean updateProduct(String id, Product updatedProduct, UserDetails userDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for this id :: " + id));
        User authenticatedUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the authenticated user is an admin or the owner of the product
        if (authenticatedUser.getRole().equals(Role.ROLE_ADMIN) || existingProduct.getUserId().equals(authenticatedUser.getId())) {
            // Update only the fields that are not null in the updatedProduct
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getPrice() != null) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }
            productRepository.save(existingProduct);
            return true;
        } else {
            return false;
        }
    }
}
