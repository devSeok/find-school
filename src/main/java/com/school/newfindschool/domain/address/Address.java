package com.school.newfindschool.domain.address;


import com.school.newfindschool.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
// 직접 생성해서 사용할 일이 없으므로 추상 클래스로 만드는 것을 권장
//출처: https://ict-nroo.tistory.com/129 [개발자의 기록습관:티스토리]
public abstract class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressName;

}
