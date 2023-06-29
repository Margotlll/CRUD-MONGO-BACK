package com.tutorial.crudmongoback.security.service;

import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    //obtiene los usuarios de los documentos de mongo
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity=userRepository.findByUsernameOrEmail(username,username);
        if (!userEntity.isPresent())
            return null;
        return UserPrincipal.build(userEntity.get());
    }
}
