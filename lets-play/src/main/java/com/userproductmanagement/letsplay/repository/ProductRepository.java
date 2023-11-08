package com.userproductmanagement.letsplay.repository;

import com.userproductmanagement.letsplay.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {



}
