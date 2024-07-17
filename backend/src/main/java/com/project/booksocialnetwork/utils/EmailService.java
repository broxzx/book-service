package com.project.booksocialnetwork.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void sendVerificationEmail(String subject, String email, String token) {
        String resetPasswordUrl = "http://localhost:8088/api/v1/users/verify?token=%s".formatted(token);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setSubject("Reset your password");
        simpleMailMessage.setText("To reset your password please click on following link: %s".formatted(resetPasswordUrl));

        mailSender.send(simpleMailMessage);
    }

    @Autowired
    public void setSimpleMailMessage(@Lazy SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

}
