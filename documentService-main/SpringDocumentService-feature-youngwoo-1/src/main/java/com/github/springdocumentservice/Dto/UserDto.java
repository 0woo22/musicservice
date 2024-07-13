package com.github.springdocumentservice.Dto;

import com.github.springdocumentservice.Dto.Role;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String userPassword;
    private String userNickname;
    private String userStatus;
    private String favorite;
    private String userImg;
    private final Role role = Role.USER;


}