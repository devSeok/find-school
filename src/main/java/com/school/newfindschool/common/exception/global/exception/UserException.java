package com.school.newfindschool.common.exception.global.exception;

public class UserException extends BusinessException{
    public UserException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
