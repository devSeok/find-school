package com.school.newfindschool.auth.oauth2.oauthManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthProviderFactory {

    private final KaKaoOAuthProvider kaKaoOAuthProvider;
    private final NaverOAuthProvider naverOAuthProvider;
    private final FacebookOAuthProvider facebookOAuthProvider;

    public OAuthProvider getOAuthProvider(String providerName) {
        if (providerName.equalsIgnoreCase(KaKaoOAuthProvider.NAME)) {
            return kaKaoOAuthProvider;
        }
        if (providerName.equalsIgnoreCase(NaverOAuthProvider.NAME)) {
            return naverOAuthProvider;
        }
        if (providerName.equalsIgnoreCase(FacebookOAuthProvider.NAME)){
            return facebookOAuthProvider;
        }
        throw new IllegalArgumentException("미지원");
    }

}
