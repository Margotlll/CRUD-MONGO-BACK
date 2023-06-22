package com.tutorial.crudmongoback.security.service;

import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.global.utils.Operations;
import com.tutorial.crudmongoback.security.dto.UserDto;
import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.enums.RoleEnum;
import com.tutorial.crudmongoback.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }
    public UserEntity create(UserDto userDto) throws AttributeException {
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new AttributeException("User name already in use");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new AttributeException("Email already in use");
        }

        return userRepository.save(mapUserFromDto(userDto));
    }
    private UserEntity mapUserFromDto(UserDto userDto){
        int id= Operations.autoIncrement(userRepository.findAll());
        //Convertir la Lista String del dto a tipo RolEnum
        List<RoleEnum> roles= userDto.getRoles().stream().map(rol -> RoleEnum.valueOf(rol)).collect(Collectors.toList());
       return new UserEntity(id,userDto.getUsername(),userDto.getEmail(),userDto.getPassword(),roles);

    }





}


