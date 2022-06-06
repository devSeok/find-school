package com.school.newfindschool.common.exception.global.exception;

public class UserOutException extends BusinessException{

    public UserOutException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
