package com.tutorial.crudmongoback.security.service;

import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.global.utils.Operations;
import com.tutorial.crudmongoback.security.dto.CreateUserDto;
import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.enums.RoleEnum;
import com.tutorial.crudmongoback.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }
    public UserEntity create(CreateUserDto createUserDto) throws AttributeException {
        if(userRepository.existsByUsername(createUserDto.getUsername())){
            throw new AttributeException("User name already in use");
        }
        if(userRepository.existsByEmail(createUserDto.getEmail())){
            throw new AttributeException("Email already in use");
        }

        return userRepository.save(mapUserFromDto(createUserDto));
    }
    private UserEntity mapUserFromDto(CreateUserDto createUserDto){
        int id= Operations.autoIncrement(userRepository.findAll());
        String password= passwordEncoder.encode(createUserDto.getPassword());
        //Convertir la Lista String del dto a tipo RolEnum
        List<RoleEnum> roles= createUserDto.getRoles().stream().map(rol -> RoleEnum.valueOf(rol)).collect(Collectors.toList());
       return new UserEntity(id, createUserDto.getUsername(), createUserDto.getEmail(),password,roles);

    }





}


