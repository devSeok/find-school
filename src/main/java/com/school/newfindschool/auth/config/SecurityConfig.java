package com.school.newfindschool.auth.config;


import com.school.newfindschool.config.jwt.JwtAuthenticationFilter;
import com.school.newfindschool.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable() // csrf 보안 토큰 disable처리.

                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.

        http
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/api/v1/**","/swagger-ui/**","/swagger-resources/**",
                        "/v2/**", "/genrateQRCode", "/oauth", "/users/**", "/iamport/**", "/mybatisTest", "/login.html").permitAll()
                .anyRequest().authenticated(); // 그외 나머지 요청은 누구나 접근 가능

        http
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        log.info("access Denied");
//                        CommonUtil.response(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
                        return;
                    }
                })
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        log.info("authentication Entry");
//                        CommonUtil.response(response, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
                        return;
                    }
                })
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class);
    }



}
