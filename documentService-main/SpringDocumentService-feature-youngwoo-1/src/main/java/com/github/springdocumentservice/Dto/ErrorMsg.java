package com.github.springdocumentservice.Dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMsg {

    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED,"인증 토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 토큰 정보입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"만료된 토큰 정보입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,"지원하지 않는 토큰 방식입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,"요청이 거절되었습니다."),
    AUTHORIZE_DENIED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");

    Integer status;
    String messages;

    ErrorMsg(HttpStatus httpStatus, String s) {
        status = httpStatus.value();
        this.messages = s;
    }

    public String getMsg(){
        return this.messages;
    }

}