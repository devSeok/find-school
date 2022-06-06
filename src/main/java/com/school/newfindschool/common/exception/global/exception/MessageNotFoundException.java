package com.school.newfindschool.common.exception.global.exception;

public class MessageNotFoundException extends BusinessException{



    public MessageNotFoundException(String errorCode) {
        super(errorCode, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
