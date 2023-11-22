package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.Product;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts();

    Optional<Product> getProductById(String id);

    Optional<Product> getProductsByUserId(String id);

    Product addProduct(UserDetails userDetails, Product product);

    Boolean deleteProduct(String id);

    Boolean updateProduct(String id, Product product, UserDetails userDetails);
}
