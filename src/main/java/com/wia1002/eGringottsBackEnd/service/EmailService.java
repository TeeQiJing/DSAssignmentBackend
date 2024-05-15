package com.wia1002.eGringottsBackEnd.service;

import com.wia1002.eGringottsBackEnd.model.EmailDetails;


public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}

