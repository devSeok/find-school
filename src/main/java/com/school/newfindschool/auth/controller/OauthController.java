package com.school.newfindschool.auth.controller;


import com.school.newfindschool.auth.oauth2.Social;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProvider;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProviderFactory;
import com.school.newfindschool.config.jwt.JwtTokenProvider;
import com.school.newfindschool.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OauthController {

    private final OAuthProviderFactory oAuthProviderFactory;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login/oauth2/{provider}")
    public void oauthLogin(@PathVariable String provider, String code) {

        OAuthProvider oAuthProvider = oAuthProviderFactory.getOAuthProvider(provider);
        Social user = oAuthProvider.requestSocialLoginUser(code);

        memberService.login(user);
//        System.out.println(user.toString());
    }

    @PostMapping("/login")
    public void Token() {
        String id = "22";
        String role = "2222";
        String accessToken = jwtTokenProvider.createAccessToken(id, role);

        System.out.println(accessToken);
    }
}
