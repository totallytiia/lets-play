package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(String id);

    Product addProduct(Product product);

    Boolean deleteProduct(String id);

    Boolean updateProduct(String id, Product product);
}
