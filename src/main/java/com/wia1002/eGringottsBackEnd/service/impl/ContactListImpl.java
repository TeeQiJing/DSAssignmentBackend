package com.wia1002.eGringottsBackEnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.model.ContactDTO;
import com.wia1002.eGringottsBackEnd.model.ContactList;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.ContactListRepository;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.ContactListService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactListImpl implements ContactListService{
    @Autowired
    private ContactListRepository contactListRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserAvatarRepository userAvatarRepository ;
    
    @Override
    public ResponseEntity<ContactList> createContact(String username,ContactList contactList){
        contactList.setUsername(username);
        
        ContactList savedContactList=contactListRepository.save(contactList);
        return savedContactList;
    }
    @Override
    public List<ContactDTO> getAllContact(String username){
        List<ContactList> contactList=contactListRepository.getContactLists(username);
        List<ContactDTO> contactDTO=new ArrayList<>() ;
        for(ContactList list:contactList){
            contactDTO.add(new ContactDTO(list.getContact_mobile(),list.getUser_created_name(),list.getAccount_number(),userAvatarRepository.getUserAvatars(list.getAccount_number())));
        }
        System.out.println(contactDTO.toString());
        return contactDTO;

    }
    @Override
    public List<ContactDTO> getContactListByCategory(String username, String category){
        List<ContactList> contactList=contactListRepository.getContactListsByCategory(username,category);
        List<ContactDTO> contactDTO=new ArrayList<>() ;
        for(ContactList list:contactList){
            contactDTO.add(new ContactDTO(list.getContact_mobile(),list.getUser_created_name(),list.getAccount_number(),userAvatarRepository.getUserAvatars(list.getAccount_number())));
        }
        
        // if(category.split("")[0].equals("0")&&category.split("")[1].equals("1"))
        // contactList=contactListRepository.getContactListsByMobile(username, category);
        // else if(category.split("")[0].equals("1"))
        // contactList=contactListRepository.getContactListsByAccountNumber(username, category);
        // else
        // contactList=contactListRepository.getContactListsByUserCreatedName(username, category);
        return contactDTO;
    }
    // @Override
    // public boolean isNewContact(String username,String test){
    //     return contactListRepository.getPeopleFromContact(username,test).isEmpty() &&!accountRepository.getPeopleFromAccount(test).isEmpty();
    // }
    @Override
    public boolean isNewContact(String username,String account_number, String mobile){
        return contactListRepository.getPeopleFromContact(username,account_number, mobile).isEmpty();
    }

    @Override
    public boolean isRegistered(String account_number,String mobile){
        return accountRepository.findByAccountNumberAndMobile(account_number, mobile).size()==1;
    }


}
