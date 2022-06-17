package com.school.newfindschool.domain.address.inheritances;

import com.school.newfindschool.domain.address.Address;
import com.school.newfindschool.domain.address.YearAttributeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.Year;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("School")
public class School extends Address {

    @Convert(converter = YearAttributeConverter.class)
    private Year year;
}
