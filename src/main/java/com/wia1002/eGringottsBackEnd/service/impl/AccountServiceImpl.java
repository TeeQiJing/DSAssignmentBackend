package com.wia1002.eGringottsBackEnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.model.ConfirmationToken;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.CardRepository;
import com.wia1002.eGringottsBackEnd.repository.ConfirmationTokenRepository;
import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.AccountService;
import com.wia1002.eGringottsBackEnd.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
// @RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserAvatarRepository userAvatarRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    
  

    @Override
    public Account getAccountById(String account_number){
        
       return accountRepository.findById(account_number).orElseThrow(
                () -> new RuntimeException("Account does not exist with the given Account Number: " + account_number));
        
    }

    @Override
    public Account createAccount(Account account) {
        if (account.getBalance() >= 1000000) {
            account.setTrans_limit(10000);
            account.setAccount_type("Platinum Patronus");
            account.setInterest_rate(0.10);
        } else if (account.getBalance() >= 300000) {
            account.setTrans_limit(8000);
            account.setAccount_type("Golden Galleon");
            account.setInterest_rate(0.04);
        } else {
            account.setTrans_limit(5000);
            account.setAccount_type("Silver Snitch");
            account.setInterest_rate(0.02);
        }

        // account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        
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
    public ResponseEntity<?> saveAccount(Account account) {
        // if (accountRepository.existsByEmail(account.getEmail())) {
        //     return ResponseEntity.badRequest().body("Error: Email is already in use!");
        // }
        // accountRepository.save(account);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setAccountNumber(account.getAccount_number());
        confirmationToken.setConfirmationToken(ConfirmationToken.generateRandomString());

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(account.getEmail());
        mailMessage.setSubject("Verification Token to Complete E-Gringotts Account Registration");
        mailMessage.setText("Thanks for Signing Up an E-Gringotts Account with Us!\nThis is your Confirmation Token (Case-Sensitive) : "+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Account account = accountRepository.findById(token.getAccountNumber()).get();
            account.setEnabled(true);
            accountRepository.save(account);
            confirmationTokenRepository.delete(token);

            return ResponseEntity.ok("Email verified successfully! You will be redirected to login page.");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }



}
