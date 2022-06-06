package com.school.newfindschool.common.exception.global.exception;

public class TokenException extends BusinessException{
    public TokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
