package com.wia1002.eGringottsBackEnd.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.ContactDTO;
import com.wia1002.eGringottsBackEnd.model.ContactList;
import com.wia1002.eGringottsBackEnd.service.ContactListService;

@RestController
@AllArgsConstructor
@RequestMapping("ContactList")
@CrossOrigin
public class ContactListController {
    private ContactListService contactListService;

    @PostMapping("/{username}/add")
    public ResponseEntity<ContactList> addContact(@PathVariable("username") String username, @RequestBody ContactList contactList) {
        ResponseEntity<ContactList> response = contactListService.isNewContact(username, contactList);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            contactListService.createContact(username,contactList);
            
        } 
        return response;


       
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<ContactDTO>> getAllContactList(@PathVariable("username") String username){
        List<ContactDTO> contactList =contactListService.getAllContact(username);
        return ResponseEntity.ok(contactList);
    }
    @GetMapping("/{username}/{category}")
    public ResponseEntity<List<ContactDTO>> getContactByCatogory(@PathVariable("username") String username, @PathVariable("category") String category){
        List<ContactDTO> contactList=contactListService.getContactListByCategory(username, category);
        return ResponseEntity.ok(contactList);
    }


}
