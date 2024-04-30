package com.wia1002.eGringottsBackEnd.service;

import com.wia1002.eGringottsBackEnd.model.Account;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(String account_number);
}
