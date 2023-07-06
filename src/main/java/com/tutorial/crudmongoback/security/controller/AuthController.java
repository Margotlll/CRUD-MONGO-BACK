package com.tutorial.crudmongoback.security.controller;

import com.tutorial.crudmongoback.global.dto.MessageDto;
import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.security.dto.CreateUserDto;
import com.tutorial.crudmongoback.security.dto.JwtTokenDto;
import com.tutorial.crudmongoback.security.dto.LoginUserDto;
import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")

@CrossOrigin //para ver en angular
public class AuthController {
    @Autowired
    UserEntityService userEntityService;
    @PostMapping("/create")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody CreateUserDto createUserDto) throws AttributeException {
       UserEntity userEntity;
        userEntity = userEntityService.create(createUserDto);
        String message="user "+userEntity.getUsername()+" has been saved";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));

    }
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody LoginUserDto dto) throws AttributeException {
        JwtTokenDto jwtTokenDto = userEntityService.login(dto);
        return ResponseEntity.ok(jwtTokenDto);

    }

}
