package com.school.newfindschool.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    // 키
    private String secretKey = "secret";

    // 어세스 토큰 유효시간 | 1시간
    private long accessTokenValidTime = 60 * 60 * 1000L; // 30 * 60 * 1000L;
    // 리프레시 토큰 유효시간 | 24시간
    private long refreshTokenValidTime = 24 * 60 * 60 * 1000L;

//    private final RedisRepository redisRepository;

    // 의존성 주입 후, 초기화를 수행
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Access Token 생성.
    public String createAccessToken(String email, String roles){
        return this.createToken(email, roles, accessTokenValidTime);
    }
    // Refresh Token 생성.
    public String createRefreshToken(String email, String roles) {
        return this.createToken(email, roles, refreshTokenValidTime);
    }

    // Create token
    public String createToken(String userId, String roles, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(userId); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장

        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }


    public String getPayload(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e){
            throw new RuntimeException("유효하지 않은 토큰 입니다");
        }
    }
//
    // JWT 에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
//        UserDto userDetails = (UserDto) memberService.loadUserByUsername(this.getUserId(token))
//        ;
        UserDetails userDetails = getTokenUser(token);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getTokenUser(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new User(this.getUserId(token), "", Collections.singleton(new SimpleGrantedAuthority(claims.get("roles").toString())));
    }


    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

//    // Request의 Header에서 AccessToken 값을 가져옵니다. "authorization" : "token'
    public String resolveAccessToken(HttpServletRequest request) {
        return resolveToken(request, "authorization");
    }
    // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
    public String resolveRefreshToken(HttpServletRequest request) {
        return resolveToken(request, "refreshToken");
    }

    private String resolveToken(HttpServletRequest request, String tokenName){
        String token = request.getHeader(tokenName);
        if(token != null && !token.isEmpty() && token.length() > 7)
            return request.getHeader(tokenName).substring(7);
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        setHeaderToken(response, accessToken, "authorization");
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        setHeaderToken(response, refreshToken, "refreshToken");
    }

    private void setHeaderToken(HttpServletResponse response, String token, String tokenName){
        response.setHeader(tokenName, "bearer "+ token);
    }

    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken) {
        return true;
    }

}
