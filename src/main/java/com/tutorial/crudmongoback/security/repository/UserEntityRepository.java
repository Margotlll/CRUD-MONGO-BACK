package com.tutorial.crudmongoback.security.repository;


import com.tutorial.crudmongoback.security.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends MongoRepository<UserEntity,Integer>{
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
}