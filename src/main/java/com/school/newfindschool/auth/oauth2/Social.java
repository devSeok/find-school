package com.school.newfindschool.auth.oauth2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class Social {

    private String providerId;
    private String providerName;
    private String name;
    private String refreshToken;
    private String email;
    private String gender;


    @Builder
    public Social(String providerId, String name, String providerName, String refreshToken, String email, String gender) {
        this.name = name;
        this.providerId = providerId;
        this.providerName = providerName;
        this.refreshToken = refreshToken;
        this.email = email;
        this.gender = gender;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        refreshToken = null;
    }

    public boolean isValidateRefreshToken(String refreshToken) {
        if (this.refreshToken == null) {
            return false;
        }
        return this.refreshToken.equals(refreshToken);
    }
}
