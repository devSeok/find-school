package com.school.newfindschool.common.exception.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),


    // Member
    EMAIL_DUPLICATION(400, "M001", "이미 가입되어 있는 유저 입니다."),
    TOKEN_DUPLICATION(400, "M002", "권한정보가 없는 토큰입니다."),
    ACCESSTOKEN_NOT_HAVE  (400, "M003", "Access Token이 존재하지 않습니다."),
    USER_NOT_FOUND(400, "M004", "유저 정보가 없습니다."),
    LOGIN_INPUT_INVALID(400, "M005", "Login input is invalid"),
    USER_OUT(400, "M006", "탈퇴한 회원입니다"),
    USER_NOT_PASSWORD(400, "M007", "패스워드가 틀렸습니다."),


    // Message
    MESSAGE_NOT_FOUND(400, "MS001", "메시지 정보가 없습니다"),

    // Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired");


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
