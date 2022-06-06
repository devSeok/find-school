package com.school.newfindschool.auth.oauth2.oauthManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.javafx.jmx.json.JSONException;
import com.school.newfindschool.auth.oauth2.Social;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
public abstract class OAuthProvider {

    protected RestTemplate restTemplate;
    protected String clientId;
    protected String clientSecret;
    protected String authorizationServerUrl;
    protected String apiServerUrl;

    public Social requestSocialLoginUser(String authorizationCode) {
        String accessToken = requestAccessToken(authorizationCode);
        return requestUserInformation(accessToken);
    }

    private String requestAccessToken(String authorizationCode) {
        try {
            ResponseEntity<String> tokenResponse = restTemplate.exchange(
                authorizationServerUrl, HttpMethod.POST, makeAccessTokenRequest(authorizationCode), String.class
            );
            return parseAccessToken(tokenResponse);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    protected Social requestUserInformation(String accessToken) {
        try {
            ResponseEntity<String> tokenResponse = restTemplate.exchange(
                apiServerUrl, HttpMethod.GET, makeUserInformationRequest(accessToken), String.class
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

    protected HttpEntity<MultiValueMap<String, String>> makeAccessTokenRequest(String authorizationCode) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(requestBody, requestHeader);
    }

    private HttpEntity<MultiValueMap<String, String>> makeUserInformationRequest(String accessToken) {
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setBearerAuth(accessToken);
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeader.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        return new HttpEntity<>(requestHeader);
    }

    protected String getJsonObject(JSONObject object, String key){
        String value = "";
        try{
            value = object.getString(key);
        }catch(JSONException e){
            value = "";
        }
        return value;
    }

    abstract protected String parseAccessToken(ResponseEntity<String> response);

    abstract protected Social parseSocialLoginUser(ResponseEntity<String> response)
        throws JsonProcessingException, ParseException;

}
