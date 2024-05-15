package com.wia1002.eGringottsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderDemo {
     @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String toEmail,
                              String subject,
                              String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setFrom("qijingtee1227@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Set the second parameter to true to indicate HTML content
            mailSender.send(message);
            System.out.println("Mail Sent...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
