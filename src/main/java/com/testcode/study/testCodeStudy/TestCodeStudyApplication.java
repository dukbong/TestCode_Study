package com.testcode.study.testCodeStudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing // @WebMvcTest시 EnableJpaAuditing이 빈 등록 할 수 없다는 오류가 나기 때문에 분리한다.
@SpringBootApplication
public class TestCodeStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCodeStudyApplication.class, args);
	}

}
