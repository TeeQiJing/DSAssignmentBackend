package com.wia1002.eGringottsBackEnd.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.wia1002.eGringottsBackEnd.model.ContactDTO;
import com.wia1002.eGringottsBackEnd.model.ContactList;

public interface ContactListService {
    ContactList createContact(String username,ContactList contactList);
    List<ContactDTO> getAllContact(String account_number);
    List<ContactDTO> getContactListByCategory(String username, String category);
    boolean isNewContact(String username,String account_number, String mobile);
    boolean isRegistered(String account_number,String mobile);
}
