package com.testcode.study.testCodeStudy.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

//@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocSupport {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(
//            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider provider
    ) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) :: 문서를 만드는데 굳이 Spring 서버를 열 필요는 없다.
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
