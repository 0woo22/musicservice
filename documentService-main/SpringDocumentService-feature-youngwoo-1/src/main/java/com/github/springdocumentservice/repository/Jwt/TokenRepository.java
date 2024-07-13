package com.github.springdocumentservice.repository.Jwt;

import com.github.springdocumentservice.Dto.Token;

public interface TokenRepository {

    Token save(Token token);
    Token findByRefreshToken(String refreshToken);
    Token findByUserEmail(String userEmail);
    Token findByAccessToken(String accessToken);
    void deleteById(Long id);

}
