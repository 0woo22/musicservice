package com.github.springdocumentservice.Dto;


import com.github.springdocumentservice.service.Jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private static final String[] whitelist = {"/api/users/signup/**", "/api/users/logout","/api/users/login/**", "/api/users/logout","/api/users/signup"
            ,"/api/users/login","/swagger-ui/**", "/swagger-ui/index.html","/error","/v3/**","/api/product/**","/api/product","/images/**","/api/likes/product/**",
            "/api/likes/user/**","/api/reviews/user/**","/api/reviews/product/**","/api/reviews/seller/**", "/ws/**", "/api/all"};

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(checkPathFree(request.getRequestURI())){
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtProvider.extractRefreshToken(request)
                .filter(jwtProvider::validateToken)
                .orElse(null);

        //refreshToken이 담아져서 오는 경우 accesstoken재발급
        if (refreshToken != null) {
            jwtService.createAccessTokenHeader(response, refreshToken);
            return;
        }
        else{
            //refreshToken이 없다면 accessToken을 재발급
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    public boolean checkPathFree(String requestURI){
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        String accessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::validateToken).orElse(null);

        UserDto userDto = jwtService.checkAccessTokenValid(accessToken);
        if(userDto != null){
            this.saveAuthentication(userDto);
        }

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(UserDto userDto) {
        String password = userDto.getUserPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(userDto.getEmail())
                .password(password)
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
