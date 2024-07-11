package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.support.mail}")
    private String supportMail;

    @Value("${spring.mail.confirmation.code.time.expiration}")
    private Long expiredAt;

    private void sendEmailToClient(String subject, String email, String content) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(email);
        helper.setFrom(supportMail, "Código Certo");
        helper.setText(content, true);
        helper.setSubject(subject);

        sender.send(mimeMessage);
    }

    public EmailCodeConfirmation generateEmailCodeConfirmation() {
        return EmailCodeConfirmation.EmailCodeConfirmationBuilder.builder()
                .expiredAt(LocalDateTime.now().plusMinutes(expiredAt))
                .code(RandomStringUtils.randomAlphabetic(32))
                .build();
    }

    public void sendCodeForEmail(EmailCodeConfirmation code, String email) throws MessagingException, UnsupportedEncodingException {
        this.sendEmailToClient("Código de ativação", email, code.getCode());
    }
}