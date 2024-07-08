package com.testcode.study.testCodeStudy.api.service.mail;

import com.testcode.study.testCodeStudy.client.mail.MailSendClient;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistory;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTestMock {

    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        // Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        // given 절인데 when을 쓰는게 어색해서 BDD 스타일로 만든것이 BDDMockito.given()이며 특별한건 없이 Mockito의 when을 한번 감싼거 뿐이다.
        BDDMockito.given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // when
        boolean result = mailService.sendMail("","","","");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
     }

}