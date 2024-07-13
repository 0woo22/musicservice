package com.github.springdocumentservice.repository.Jwt;

import com.github.springdocumentservice.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByRefreshToken(String refreshToken);
    Optional<TokenEntity> findByUserEmail(String userEmail);
    TokenEntity findByAccessToken(String accessToken);
    void deleteById(Long id);

}