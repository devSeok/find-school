package com.school.newfindschool.auth.oauth2.oauthManager;


import com.school.newfindschool.auth.oauth2.Social;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Service
@Slf4j
public class FacebookOAuthProvider extends OAuthProvider {

    public static final String NAME = "FACEBOOK";

    public FacebookOAuthProvider(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Value("${oauth.facebook.client-id}")
    private void setClientId(String clientId) {
        super.clientId = clientId;
    }

    @Value("${oauth.facebook.client-secret}")
    private void setClientSecret(String clientSecret) {
        super.clientSecret = clientSecret;
    }

    @Value("${oauth.facebook.authorization-server-url}")
    private void setAuthorizationServerUrl(String authorizationServerUrl) {
        super.authorizationServerUrl = authorizationServerUrl;
    }

    @Value("${oauth.facebook.api-server-url}")
    private void setApiServerUrl(String apiServerUrl) {
        super.apiServerUrl = apiServerUrl;
    }

    @Override
    protected String parseAccessToken(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("access_token");
    }

    @Override
    protected HttpEntity<MultiValueMap<String, String>> makeAccessTokenRequest(String authorizationCode) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", "http://localhost:8080/api/v1/auth/login/oauth2/facebook");

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(requestBody, requestHeader);
    }

    @Override
    protected Social requestUserInformation(String accessToken) {
        try {
            ResponseEntity<String> tokenResponse = restTemplate.exchange(
                    apiServerUrl+"?access_token="+accessToken, HttpMethod.GET, null, String.class
            );
            return parseSocialLoginUser(tokenResponse);
        } catch (JSONException e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected Social parseSocialLoginUser(ResponseEntity<String> response) {
        JSONObject facebookAccount = new JSONObject(response.getBody());

        String name = getJsonObject(facebookAccount, "name");
        String email = getJsonObject(facebookAccount, "email");
        String gender = getJsonObject(facebookAccount, "gender");

        return Social.builder()
            .providerId(facebookAccount.getString("id"))
            .name(name)
            .email(email)
            .gender(gender)
            .providerName(NAME)
            .build();
    }

}
