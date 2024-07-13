package com.github.springdocumentservice.service.Jwt;


import com.github.springdocumentservice.Dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtService {

    void createAccessTokenHeader(HttpServletResponse response, String refreshToken);
    UserDto checkAccessTokenValid(String accessToken);
}