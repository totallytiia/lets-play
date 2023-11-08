package com.userproductmanagement.letsplay.repository;

import com.userproductmanagement.letsplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {




}
