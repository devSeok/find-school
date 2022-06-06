package com.school.newfindschool.common.exception.global.exception;

public class SignupDuplicateException extends BusinessException{

    public SignupDuplicateException(String errorCode) {
        super(errorCode, ErrorCode.EMAIL_DUPLICATION);
    }
}
