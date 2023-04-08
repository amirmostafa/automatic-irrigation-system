package org.bankmasr.irrigation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage message;

    @InjectMocks
    private MailService mailService;

    @Test
    void sendNotification_ShouldSendEmail() throws Exception {
        String body = "This is a test email";
        String subject = "Test Email";
        String[] to = {"test@example.com"};
        ReflectionTestUtils.setField(mailService, "to", to);

        when(javaMailSender.createMimeMessage()).thenReturn(message);

        mailService.sendNotification(body, subject);

        verify(javaMailSender, times(1)).send(message);
    }
}
