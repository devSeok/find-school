package com.school.newfindschool.domain.address.inheritances;

import com.school.newfindschool.domain.address.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Academy")
public class Academy extends Address {

    private String time;
}
