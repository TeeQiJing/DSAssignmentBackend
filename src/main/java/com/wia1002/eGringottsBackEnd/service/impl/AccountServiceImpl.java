package com.wia1002.eGringottsBackEnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.CardRepository;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserAvatarRepository userAvatarRepository;

    @Override
    public Account getAccountById(String account_number){
        
       return accountRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException("Account does not exist with the given Account Number: " + account_number));
        
    }

    @Override
    public Account createAccount(Account account) {
        if (account.getBalance() >= 1000000) {
            account.setTrans_limit(10000);
            account.setAccount_type("Platinum");
            account.setInterest_rate(0.10);
        } else if (account.getBalance() >= 300000) {
            account.setTrans_limit(8000);
            account.setAccount_type("Golden");
            account.setInterest_rate(0.04);
        } else {
            account.setTrans_limit(5000);
            account.setAccount_type("Silver");
            account.setInterest_rate(0.02);
        }

        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    @Override
    public List<Account> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public Account updateAccount(String account_number, Account updatedAccount) {
        Account account = accountRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException(
                        "Account is not exist with given Account Number : " + account_number));

        account.setAddress(updatedAccount.getAddress());
        account.setDob(updatedAccount.getDob());
        account.setEmail(updatedAccount.getEmail());
        account.setMobile(updatedAccount.getMobile());
        account.setPassword(updatedAccount.getPassword());
        account.setUsername(updatedAccount.getUsername());

        // Card newCard = updatedAccount.getCard();
        Card newCard = cardRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException("Card is not exist with given Account Number : " + account_number));

        newCard.setCard_num(updatedAccount.getCard().getCard_num());
        newCard.setCard_pin(updatedAccount.getCard().getCard_pin());
        newCard.setCard_type(updatedAccount.getCard().getCard_type());
        newCard.setCvv(updatedAccount.getCard().getCvv());
        newCard.setExpiry_date(updatedAccount.getCard().getExpiry_date());
        cardRepository.save(newCard);

        account.setCard(newCard);

        UserAvatar newUserAvatar = userAvatarRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException(
                        "User Avatar is not exist with given Account Number : " + account_number));
        newUserAvatar.setImage_path(updatedAccount.getUser_avatar().getImage_path());
        userAvatarRepository.save(newUserAvatar);
        account.setUser_avatar(newUserAvatar);

        account.setSecure_phrase(updatedAccount.getSecure_phrase());

        Account updatedAccountObj = accountRepository.save(account);

        return updatedAccountObj;
    }

    @Override
    public void deleteAccount(String account_number) {
        Account account = accountRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException(
                        "Account is not exist with given Account Number : " + account_number));
        cardRepository.deleteById(account_number);
        userAvatarRepository.deleteById(account_number);
        accountRepository.deleteById(account_number);

    }

    @Override
    public Account deposit(String account_number, double amount) {
        Account account=getAccountById(account_number);
        account.setBalance(account.getBalance()+amount);
        Account savedAccount=accountRepository.save(account);
        
        return savedAccount;
    }

}
