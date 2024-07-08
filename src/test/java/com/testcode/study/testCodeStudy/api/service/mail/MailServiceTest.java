package com.testcode.study.testCodeStudy.api.service.mail;

import com.testcode.study.testCodeStudy.client.mail.MailSendClient;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistory;
import com.testcode.study.testCodeStudy.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/***
 * 순수한 Mockito 테스트
 */
// @Mock 처럼 어노테이션으로 만들 경우 MockitoExtension.class를 해줘야 한다.
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Spy
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        // Mock 객체는 별도로 행위 결과를 지정하지 않으면 null, 0, empty처럼 기본적인 값이 나온다.
        // ----------- @Mock을 통해 더욱 간편하게 만들수 있다.
        // MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        // MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);
        // MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // when(mailSendClient.sendMail(any(String.class),any(String.class),any(String.class),any(String.class))).thenReturn(true);
        // 더 간단하게 작성할 수 있다.
        // when은 Spy에서는 사용할 수 없고 doxxx를 써야한다. - 49Line 참고
        // when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        // Mock객체에서 when과 비슷하지만 형태가 조금 다르다.
        // Mock객체 when과 차이는 Mock의 when은 해당 객체 전체에 대한거지만 Spy의 doxxx는 더 작은 메소드까지 조절이 가능하다.
        // Spy는 실제 객체를 사용하는 것이다.
        doReturn(true)
                .when(mailSendClient)
                .sendMail(anyString(),anyString(),anyString(),anyString());

        // when
        boolean result = mailService.sendMail("","","","");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
     }

}