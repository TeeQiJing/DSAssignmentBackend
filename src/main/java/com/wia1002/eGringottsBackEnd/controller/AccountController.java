package com.wia1002.eGringottsBackEnd.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.CardRepository;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.AccountService;
import com.wia1002.eGringottsBackEnd.service.CardService;
import com.wia1002.eGringottsBackEnd.service.UserAvatarService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("account")
@CrossOrigin("http://localhost:3000")
public class AccountController {

    private CardService cardService;
    private UserAvatarService userAvatarService;
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    // @Autowired
    // private CardRepository cardRepository;

    // private CardRepository cardRepository;
    // private UserAvatarRepository userAvatarRepository;

   
    @GetMapping("{account_number}")
    public ResponseEntity<Account> getAccountById(@PathVariable("account_number") String account_number) {
        Account account = accountService.getAccountById(account_number);
        account.setCard(
                cardService.getCardById(account_number));
        account.setUser_avatar(userAvatarService.getUserAvatarById(account_number));
        return ResponseEntity.ok(account);
    }
    @PostMapping("/addaccount")
    public ResponseEntity<Account> newAccount(@RequestBody Account newAccount) {
        if(accountRepository.findById(newAccount.getAccount_number()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);;
        
        Account account = accountService.createAccount(newAccount);
        
        return ResponseEntity.ok(account);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccount() {
        List<Account> accounts = accountService.getAllAccount();
        for (Account account : accounts) {
            account.setCard(cardService.getCardById(account.getAccount_number()));
            account.setUser_avatar(userAvatarService.getUserAvatarById(account.getAccount_number()));
        }
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/update/{account_number}")
    public ResponseEntity<Account> updateAccount(@PathVariable("account_number") String account_number,
            @RequestBody Account updateAccount) {

        updateAccount.getCard().setAccount_number(account_number);
        updateAccount.getUser_avatar().setAccount_number(account_number);
        Account updatedAccount = accountService.updateAccount(account_number, updateAccount);

        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/delete/{account_number}")
    public ResponseEntity<String> deleteAccount(@PathVariable("account_number") String account_number) {
        accountService.deleteAccount(account_number);
        return ResponseEntity.ok("Account deleted successfully!");

    }

    @GetMapping("/login")
    public ResponseEntity<Account> getAccountByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        List<Account> accounts = accountRepository.findByUsernameAndPassword(username, password);

        if (accounts.size() == 1) {
            Account account = accounts.get(0);

            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
