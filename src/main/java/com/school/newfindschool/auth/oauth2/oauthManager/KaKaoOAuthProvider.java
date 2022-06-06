package com.school.newfindschool.auth.oauth2.oauthManager;

import com.school.newfindschool.auth.oauth2.Social;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Getter
@Service
public class KaKaoOAuthProvider extends OAuthProvider {

    public static final String NAME = "KAKAO";

    public KaKaoOAuthProvider(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Value("${oauth.kakao.client-id}")
    private void setClientId(String clientId) {
        super.clientId = clientId;
    }

    @Value("${oauth.kakao.client-secret}")
    private void setClientSecret(String clientSecret) {
        super.clientSecret = clientSecret;
    }

    @Value("${oauth.kakao.authorization-server-url}")
    private void setAuthorizationServerUrl(String authorizationServerUrl) {
        super.authorizationServerUrl = authorizationServerUrl;
    }

    @Value("${oauth.kakao.api-server-url}")
    private void setApiServerUrl(String apiServerUrl) {
        super.apiServerUrl = apiServerUrl;
    }

    @Override
    protected String parseAccessToken(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("access_token");
    }

    @Override
    protected Social parseSocialLoginUser(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
        JSONObject profile = (JSONObject) kakaoAccount.get("profile");

        String email = getJsonObject(kakaoAccount, "email");
        String gender = getJsonObject(kakaoAccount, "gender");

        Social social = Social.builder()
            .name(profile.getString("nickname"))
            .email(email)
            .gender(gender)
            .providerId(String.valueOf(jsonObject.getLong("id")))
            .providerName(NAME)
            .build();
        return social;
    }

}


