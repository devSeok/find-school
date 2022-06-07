package com.school.newfindschool.auth.controller;


import com.school.newfindschool.auth.oauth2.Social;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProvider;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OauthController {

    private final OAuthProviderFactory oAuthProviderFactory;

    @GetMapping("/login/oauth2/{provider}")
    public void oauthLogin(@PathVariable String provider, String code) {

        OAuthProvider oAuthProvider = oAuthProviderFactory.getOAuthProvider(provider);
        Social user = oAuthProvider.requestSocialLoginUser(code);

        System.out.println(user.toString());
    }
}
