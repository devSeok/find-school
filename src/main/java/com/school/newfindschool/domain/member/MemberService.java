package com.school.newfindschool.domain.member;

import com.school.newfindschool.auth.dto.response.LoginResponse;
import com.school.newfindschool.auth.oauth2.Social;
import com.school.newfindschool.common.exception.global.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member login(Social social) {
        String providerId = social.getProviderId();

        if (!memberRepository.existsByProviderId(providerId)) {
            Member member = Member.createMember(social);
            memberRepository.save(member);
        }

        Member findMember = memberRepository
                .findByProviderId(social.getProviderId())
                .orElseThrow(() -> new UserException("정보가 없습니다."));


        return findMember;
    }
}
