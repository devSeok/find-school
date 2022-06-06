package com.school.newfindschool.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class OauthController {

    @GetMapping("/login/oauth2/{provider}")
    public void oauthLogin(@PathVariable String provider, String code) {

        System.out.println(code);
        System.out.println(provider);
    }
}
