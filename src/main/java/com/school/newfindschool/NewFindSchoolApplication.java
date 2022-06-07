package com.school.newfindschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewFindSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewFindSchoolApplication.class, args);
    }

}
