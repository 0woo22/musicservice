package com.github.springdocumentservice.domain;

import com.github.springdocumentservice.Dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "user_nickname", nullable = false, length = 255)
    private String userNickname;

    @Column(name="user_status", nullable = false , length =255)
    private String userStatus;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public static User from(UserDto userDto){
        return User.builder()
                .email(userDto.getEmail())
                .userPassword(userDto.getUserPassword())
                .userNickname(userDto.getUserNickname())
                .userStatus(userDto.getUserStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserDto toDTO(){
        return UserDto.builder()
                .email(this.email)
                .userPassword(this.userPassword)
                .userNickname(this.userNickname)
                .userStatus(this.userStatus)
                .userId(this.userId)
                .build();
    }
}