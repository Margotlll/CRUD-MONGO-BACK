package com.tutorial.crudmongoback.security.repository;

import com.tutorial.crudmongoback.CRUD.entity.Product;


import com.tutorial.crudmongoback.security.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends MongoRepository<UserEntity,Integer>{
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}