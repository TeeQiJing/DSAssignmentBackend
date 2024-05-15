package com.wia1002.eGringottsBackEnd.controller;

import java.util.List;
import java.util.Map;

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

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.service.AccountService;
import com.wia1002.eGringottsBackEnd.service.CardService;
import com.wia1002.eGringottsBackEnd.service.TransactionService;
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
    private TransactionService transactionService;
    

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
    // @PutMapping("/deposit/{account_number}")
    // public ResponseEntity<Account> deposit(@PathVariable("account_number") String account_number,@RequestBody Map<String,Double>request){
    //     double amount=request.get("amount");
    //     Account account=accountService.deposit(account_number,amount); 
    //     transactionService.createTransactionDeposit(account_number,amount);
    //     return ResponseEntity.ok(account);
    // }

    @GetMapping("{account_number}")
    public ResponseEntity<Account> getAccountById(@PathVariable("account_number") String account_number) {
        Account account = accountService.getAccountById(account_number);
        account.setCard(
                cardService.getCardById(account_number));
        account.setUser_avatar(userAvatarService.getUserAvatarById(account_number));
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
    public ResponseEntity<String> deleteAccount(@PathVariable("account_number") String account_number){
        accountService.deleteAccount(account_number);
        return ResponseEntity.ok("Account deleted successfully!");

    }

 


}
