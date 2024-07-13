package com.github.springdocumentservice.controller;


import com.github.springdocumentservice.Dto.NewUserDto;
import com.github.springdocumentservice.Dto.ResponseToken;
import com.github.springdocumentservice.Dto.Token;
import com.github.springdocumentservice.Dto.UserDto;
import com.github.springdocumentservice.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    @Operation(summary = "유저 회원가입", description = "이메일, 비밀번호, 닉네임, 휴대폰번호, 관심분야, 프로필 사진을 넣고 회원가입합니다.")
    public ResponseEntity<?> join(
            @RequestParam("email") String email,
            @RequestParam("password") String userPassword,
            @RequestParam("nickname") String userNickname,
            @RequestParam("status")String userStatus,
            @RequestParam("favorite")String favorite
    ) {

        try {

            NewUserDto newUserDto = NewUserDto.builder()
                    .email(email)
                    .userPassword(userPassword)
                    .userNickname(userNickname)
                    .userStatus(userStatus)
                    .favorite(favorite)
                    .build();

            // 유효성 검사
            if (!isValidEmail(newUserDto.getEmail())) {
                log.info("이메일 형식에 맞게 입력하세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 형식에 맞게 입력하세요.");
            }

            if (!isValidPassword(newUserDto.getUserPassword())) {
                log.info("비밀번호 형식에 맞게 입력해주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 형식에 맞게 입력해주세요.");
            }


            // 사용자 등록
            UserDto savedUser = userService.register(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(savedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/users/login")
    @Operation(summary = "유저 로그인", description = "유저 이메일, 비밀번호를 이용하여 로그인합니다.")
    public ResponseEntity<?> login(@RequestBody UserDto login) {
        try {
            String email = login.getEmail();
            String password = login.getUserPassword();
            Token token = userService.login(email, password);
            return ResponseEntity.ok().body(ResponseToken.of(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/users/logout")
    @Operation(summary = "유저 로그아웃", description = "해당 이메일을 가진 유저를 로그아웃합니다.")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestBody UserDto userDto) {
        String res = userService.logout(request, userDto.getEmail());
        return ResponseEntity.ok().body(res);
    }



    //이메일 유효성 검사
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    //비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        String passwordRegex = "(?=.*[0-9])(?=.*[A-Za-z]).{8,20}$";
        return password.matches(passwordRegex);
    }



}