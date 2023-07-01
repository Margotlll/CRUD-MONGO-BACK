package com.tutorial.crudmongoback.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateUserDto {
    @NotBlank(message = "Name is mandatory")
    private String username;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private  String password;

    @NotEmpty(message = "role are mandatory")
    List<String> roles;

    public CreateUserDto() {
    }

    public CreateUserDto(String username, String email, String password, List<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
