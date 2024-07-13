package com.github.springdocumentservice.service.User;

import com.github.springdocumentservice.Dto.NewUserDto;
import com.github.springdocumentservice.Dto.Token;
import com.github.springdocumentservice.Dto.UserDto;
import com.github.springdocumentservice.controller.UserResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface UserService {


    String encodePassword(String password);


    boolean matchesPassword(String rawPassword, String encodedPassword);

    UserDto register(NewUserDto userDto) throws IOException;


    Token login(String email, String pw) throws Exception;
    public String logout(HttpServletRequest request, String email);


    UserDto findUserByEmail(String email);


}