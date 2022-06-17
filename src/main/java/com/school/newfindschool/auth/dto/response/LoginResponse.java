package com.school.newfindschool.auth.dto.response;

import com.school.newfindschool.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String nickName;

    public static LoginResponse from(Member member) {
        return new LoginResponse(member.getNickName());
    }
}
