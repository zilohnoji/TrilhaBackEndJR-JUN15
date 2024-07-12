package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    private void sendEmailToClient(String subject, String email, String content) throws UnsupportedEncodingException, MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setFrom(supportMail, "Código Certo");
        helper.setText(content, true);
        helper.setSubject(subject);

        sender.send(message);
    }

    public void sendCodeForEmail(EmailCodeConfirmation code, String email) throws MessagingException, UnsupportedEncodingException {
        this.sendEmailToClient("Código de ativação", email, this.templateHtmlForSendCode(code.getCode(), code.getExpiredAt()));
    }

    private String templateHtmlForSendCode(String code, LocalDateTime expiredAt) {
        return "<!DOCTYPE html>" +
                "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office'>" +
                "<head>" +
                "  <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>" +
                "  <meta name='viewport' content='width=device-width'>" +
                "  <style>" +
                "    * {" +
                "      padding: 0rem;" +
                "      overflow: hidden;" +
                "      margin: 0;" +
                "      background-color: #141313;" +
                "    }" +
                "    p {" +
                "      color: #fff;" +
                "      margin-bottom: 5px;" +
                "    }" +
                "    img {" +
                "      max-width: 100%;" +
                "    }" +
                "    h1 {" +
                "      color: #b8b8b8;" +
                "      margin-top: 1rem;" +
                "      margin-bottom: 1rem;" +
                "      margin-left: 1rem;" +
                "      font-family: sans-serif;" +
                "    }" +
                "    .container-codigo {" +
                "      font-family: Arial, Helvetica, sans-serif;" +
                "      font-size: 1rem;" +
                "      margin-left: 1rem;" +
                "    }" +
                "    .green {" +
                "      color: green;" +
                "    }" +
                "    .yellow {" +
                "      color: yellow;" +
                "    }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <img src='https://utfs.io/f/3b2340e8-5523-4aca-a549-0688fd07450e-j4edu.jfif' alt=''>" +
                "  <div>" +
                "    <h1>Código de ativação da conta</h1>" +
                "  </div>" +
                "  <div class='container-codigo'>" +
                "    <p>Código: <span class='green'>" + code + "</span></p>" +
                "    <p>Tempo de expiração: <span class='yellow'>" + (LocalDateTime.now().getMinute() - expiredAt.getMinute()) + "min</span></p>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }
}