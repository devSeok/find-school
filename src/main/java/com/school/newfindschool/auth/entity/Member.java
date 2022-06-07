package com.school.newfindschool.auth.entity;

import com.school.newfindschool.config.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    private String gender;

    @Column(nullable = true)
    private String userEmail;

    @Column(nullable = true)
    private String nickName;

    @Column(nullable = true)
    private String providerId;

    @Column(nullable = true)
    private String providerName;


}
