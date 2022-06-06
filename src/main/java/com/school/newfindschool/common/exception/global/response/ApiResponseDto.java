package com.school.newfindschool.common.exception.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiResponseDto {

    private String message;
    private int status;
    private Object data = new Object();
}
