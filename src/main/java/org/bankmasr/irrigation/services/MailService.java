package org.bankmasr.irrigation.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailService implements INotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${notification.receivers}")
    private String[] to;

    @Override
    public void sendNotification(String body, String subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("error while sending email", e);
        }
    }
}
