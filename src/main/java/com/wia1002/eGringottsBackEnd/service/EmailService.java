package com.wia1002.eGringottsBackEnd.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.model.EmailDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("emailService")
public class EmailService {

     @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

   private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendTransactionReceipt(String toEmail, ByteArrayInputStream pdfStream) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Write the content of the ByteArrayInputStream to a ByteArrayOutputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = pdfStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Transaction Receipt 🎩");
            helper.setText("Thank you for using E-Gringotts! Your magical transfer has been successfully completed. Please find the attached transaction receipt. 🪄"+
            "\n\nFor any inquiries or further assistance, owl us via this email."+
            "\n\nMay your galleons multiply like Fizzing Whizbees! ✨");

            // Attach the PDF using the content from the ByteArrayOutputStream
            helper.addAttachment("TransactionReceipt.pdf", new ByteArrayResource(outputStream.toByteArray()));

            // Send the email
            javaMailSender.send(message);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        } finally {
            // Ensure the InputStream is closed
            if (pdfStream != null) {
                try {
                    pdfStream.close();
                } catch (IOException e) {
                    // Handle IOException while closing the stream
                    e.printStackTrace();
                }
            }
        }
    }

}