package com.school.newfindschool.auth.controller;


import com.school.newfindschool.auth.dto.response.LoginResponse;
import com.school.newfindschool.auth.oauth2.Social;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProvider;
import com.school.newfindschool.auth.oauth2.oauthManager.OAuthProviderFactory;
import com.school.newfindschool.common.exception.global.response.ApiResponseDto;
import com.school.newfindschool.config.jwt.JwtTokenProvider;
import com.school.newfindschool.domain.member.Member;
import com.school.newfindschool.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OauthController {

    private final OAuthProviderFactory oAuthProviderFactory;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login/oauth2/{provider}")
    public ResponseEntity<ApiResponseDto<LoginResponse>> oauthLogin(@PathVariable String provider, String code) {

        OAuthProvider oAuthProvider = oAuthProviderFactory.getOAuthProvider(provider);
        Social user = oAuthProvider.requestSocialLoginUser(code);

        Member login = memberService.login(user);

        return ResponseEntity.ok(
                ApiResponseDto.<LoginResponse>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(LoginResponse.from(login))
                        .build()
        );
    }



    // 토큰 발급
    @PostMapping("/login")
    public void Token() {
        String id = "22";
        String role = "2222";
        String accessToken = jwtTokenProvider.createAccessToken(id);

        System.out.println(accessToken);
    }
}
