package com.testcode.study.testCodeStudy.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        log.info("메일 전송");
        // Mock으로 행위검증을 테스트 하기위해 일부러 Exception 발생시킴
        throw new IllegalArgumentException("메일 전송");
    }

    // @Spy를 테스트 하기 위함
    public void a(){log.info("A");}
    public void b(){log.info("B");}
    public void c(){log.info("C");}

}
