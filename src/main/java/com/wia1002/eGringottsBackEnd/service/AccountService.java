package com.wia1002.eGringottsBackEnd.service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.wia1002.eGringottsBackEnd.model.Account;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(String account_number);
    List<Account> getAllAccount();
    Account updateAccount(String account_number, Account updatedAccount);
    void deleteAccount(String account_number);
  
    ResponseEntity<?> saveAccount(Account account);

    ResponseEntity<?> confirmEmail(String confirmationToken);
    Account deposit(String account_number, double amount);
}


