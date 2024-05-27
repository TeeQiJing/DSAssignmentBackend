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
    @PostMapping("/{username}/{self_account_number}/{self_mobile}/add")
public ResponseEntity<String> createTransaction(@PathVariable("username") String username, @PathVariable("self_account_number") String self_account_number, @PathVariable("self_mobile") String self_mobile, @RequestBody ContactList contactList) {
    if(!contactListService.isRegistered(contactList.getAccount_number(), contactList.getContact_mobile())){
        return new ResponseEntity<>("The User is Not Registered Yet!", HttpStatus.BAD_REQUEST);
    }
    if (contactListService.isNewContact(username, contactList.getAccount_number(), contactList.getContact_mobile())) {
        if (contactList.getAccount_number().equals(self_account_number) || contactList.getContact_mobile().equals(self_mobile)) {
            return new ResponseEntity<>("Cannot Add Yourself In Contact List!", HttpStatus.BAD_REQUEST);
        }
        contactListService.createContact(username, contactList);
        return new ResponseEntity<>("New Contact Added Successfully!", HttpStatus.CREATED);
    } else {
        return new ResponseEntity<>("This Account is Already in Your Contact List, Please Try Another Account", HttpStatus.BAD_REQUEST);
    }
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
