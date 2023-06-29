package com.sparta.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// 엔티티가 생성되고, 변경되는 그 시점을 감지하여 생성시각, 수정시각, 생성한 사람, 수정한 사람 기록!!@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring Security 인증 기능 제외
@SpringBootApplication
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }

}
