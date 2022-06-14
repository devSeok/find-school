package com.school.newfindschool.domain.member;

import com.school.newfindschool.auth.oauth2.Social;
import com.school.newfindschool.config.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String providerId;

    @Column(nullable = false)
    private String providerName;

    @Builder
    public Member(String gender, String userEmail, String nickName, String providerId, String providerName) {
        this.gender = gender;
        this.userEmail = userEmail;
        this.nickName = nickName;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public static Member createMember(Social social) {
        return Member.builder()
                .gender(social.getGender())
                .nickName(social.getName())
                .providerId(social.getProviderId())
                .userEmail(social.getEmail())
                .providerName(social.getProviderId())
                .build();
    }


}
