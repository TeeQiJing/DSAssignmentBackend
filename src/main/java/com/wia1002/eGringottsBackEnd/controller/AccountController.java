package com.wia1002.eGringottsBackEnd.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.service.AccountService;
import com.wia1002.eGringottsBackEnd.service.CardService;
import com.wia1002.eGringottsBackEnd.service.UserAvatarService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("account")
@CrossOrigin
public class AccountController {

    private CardService cardService;
    private UserAvatarService userAvatarService;
    private AccountService accountService;


    @PostMapping("/add")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        // Save account details
        accountService.createAccount(account);


        // Save card details
        Card card = account.getCard();
        if (card != null) {
            card.setAccount_number(account.getAccount_number());
            cardService.createCard(card);
        }

        // Save user avatar details
        UserAvatar userAvatar = account.getUser_avatar();
        if (userAvatar != null) {
            userAvatar.setAccount_number(account.getAccount_number());
            userAvatarService.createUserAvatar(userAvatar);
        }

        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    @GetMapping("{account_number}")
    public ResponseEntity<Account> getAccountById(@PathVariable("account_number") String account_number){
        Account account = accountService.getAccountById(account_number);
        account.setCard(
            cardService.getCardById(account_number)
            );
            account.setUser_avatar(userAvatarService.getUserAvatarById(account_number));
        return ResponseEntity.ok(account);
    }

}
