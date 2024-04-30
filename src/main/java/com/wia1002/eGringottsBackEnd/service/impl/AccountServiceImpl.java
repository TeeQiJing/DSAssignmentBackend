package com.wia1002.eGringottsBackEnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    

    @Override
    public Account getAccountById(String account_number){
        Account account = accountRepository.findById(account_number).orElseThrow(
            () -> new ResourceNotFoundException("Account is not exist with given Account Number : " + account_number)
        );

        return account;
    }

    @Override
    public Account createAccount(Account account) {
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }
    

}
