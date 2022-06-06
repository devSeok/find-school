package com.school.newfindschool.common.exception.global.response;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ApiRe{

    // HttpStatus
    private String status;
    // Http Default Message
    private String message;
    // Error Message to USER
   private String data;
}
