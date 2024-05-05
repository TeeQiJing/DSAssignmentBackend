package com.wia1002.eGringottsBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.wia1002.eGringottsBackEnd.model.ConfirmationToken;
import com.wia1002.eGringottsBackEnd.service.EmailSenderDemo;

import jakarta.mail.MessagingException;



@SpringBootApplication
public class EGringottsBackEndApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(EGringottsBackEndApplication.class, args);
		
		// System.out.println(ConfirmationToken.generateRandomString());
	}


}
