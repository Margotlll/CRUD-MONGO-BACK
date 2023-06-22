package com.tutorial.crudmongoback.security.controller;

import com.tutorial.crudmongoback.global.dto.MessageDto;
import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.security.dto.UserDto;
import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")

@CrossOrigin //para ver en angular
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/create")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody UserDto userDto) throws AttributeException {
       UserEntity userEntity;
        userEntity = userService.create(userDto);
        String message="user "+userEntity.getUsername()+" has been saved";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));

    }

}
