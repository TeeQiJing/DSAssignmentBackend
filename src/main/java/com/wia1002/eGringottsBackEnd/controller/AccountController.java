package com.wia1002.eGringottsBackEnd.controller;

// import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
// import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.ModelAndView;

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.ConfirmationToken;
import com.wia1002.eGringottsBackEnd.model.UserAvatar;
// import com.wia1002.eGringottsBackEnd.model.Card;
// import com.wia1002.eGringottsBackEnd.model.ConfirmationToken;
// import com.wia1002.eGringottsBackEnd.model.UserAvatar;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.ConfirmationTokenRepository;
// import com.wia1002.eGringottsBackEnd.repository.CardRepository;
// import com.wia1002.eGringottsBackEnd.repository.ConfirmationTokenRepository;
// import com.wia1002.eGringottsBackEnd.repository.UserAvatarRepository;
import com.wia1002.eGringottsBackEnd.service.AccountService;
import com.wia1002.eGringottsBackEnd.service.CardService;
import com.wia1002.eGringottsBackEnd.service.EmailSenderDemo;
// import com.wia1002.eGringottsBackEnd.service.EmailService;
import com.wia1002.eGringottsBackEnd.service.UserAvatarService;

import jakarta.mail.MessagingException;
// import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
// import lombok.RequiredArgsConstructor;

@RestController
@AllArgsConstructor
// @RequiredArgsConstructor
@RequestMapping("account")
@CrossOrigin("http://localhost:3000")
public class AccountController {

    @Autowired
    private CardService cardService;
    @Autowired
    private UserAvatarService userAvatarService;

    @Autowired
    private AccountService accountService;
    private TransactionService transactionService;
    

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
	private EmailSenderDemo senderService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    // @Autowired
    // private EmailService emailService;


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

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable("email") String email) {
        Account account = accountRepository.findByEmailIgnoreCase(email);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/addaccount")
    public ResponseEntity<Account> newAccount(@RequestBody Account newAccount) {
        if(accountRepository.findById(newAccount.getAccount_number()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
        // final HttpServletRequest request = null;
        Account account = accountService.createAccount(newAccount);
        accountService.saveAccount(account);
       

        return ResponseEntity.ok(account);
    }

    @GetMapping("gettoken/{accountNumber}")
    public ConfirmationToken getToken(@PathVariable String accountNumber) {
        return confirmationTokenRepository.findByAccountNumber(accountNumber);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyToken(@PathVariable String token) {

      
      return accountService.confirmEmail(token);

    }

    // @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    // public ResponseEntity<?> confirmUserAccount(@RequestParam("accountNumber")String accountNumber) {
    //     ConfirmationToken confirmationToken = confirmationTokenRepository.finaccountNumber).get();
    //     return ResponseEntity.ok(confirmationToken);
    // }


    // public String applicationUrl(HttpServletRequest request) {
    //    return "http://" + request.getServerName() + ":"+request.getServerPort()+request.getContextPath();
    // }
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
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        List<Account> accounts = accountRepository.findByEmailAndPassword(email, password);

        if (accounts.size() == 1) {
            Account account = accounts.get(0);

            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // @PostMapping("/sendEmail")
    // @EventListener(ApplicationReadyEvent.class)
	// public void triggerMail() throws MessagingException {
	// 	senderService.sendHtmlEmail("qijingtee1227@gmail.com",
    //     "This is email HAHAHAHA",
    //     "This is email 2<br><a href=\"http://localhost:3000/register\">Click here to register</a>");


	// }

    //   @RequestMapping(value="/register", method = RequestMethod.GET)
    // public ModelAndView displayRegistration(ModelAndView modelAndView, Account account)
    // {
    //     modelAndView.addObject("account", account);
    //     modelAndView.setViewName("register");
    //     return modelAndView;
    // }
    
    
    
    // @RequestMapping(value="/register", method = RequestMethod.POST)
    // public ModelAndView registerUser(ModelAndView modelAndView, Account account)
    // {

    // 	Account existingAccount = accountRepository.findByEmail(account.getEmail());
    //     if(existingAccount != null)
    //     {
    //         modelAndView.addObject("message","This email already exists!");
    //         modelAndView.setViewName("error");
    //     }
    //     else
    //     {
    //         accountRepository.save(account);

    //         ConfirmationToken confirmationToken = new ConfirmationToken(account);

    //         confirmationTokenRepository.save(confirmationToken);

    //         SimpleMailMessage mailMessage = new SimpleMailMessage();
    //         mailMessage.setTo(account.getEmail());
    //         mailMessage.setSubject("Complete Registration!");
    //         mailMessage.setFrom("qijingtee1227@gmail.com");
    //         mailMessage.setText("To confirm your account, please click here : "
    //         +"http://localhost:8080/account/confirm-account?token="+confirmationToken.getConfirmationToken());

    //         emailService.sendEmail(mailMessage);

    //         modelAndView.addObject("email", account.getEmail());

    //         modelAndView.setViewName("successfulRegisteration");
    //     }

    //     return modelAndView;
    // }
    

    // @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    // public ModelAndView confirmAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    // {
    //     ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

    //     if(token != null)
    //     {
    //     	Account account = accountRepository.findByEmail(token.getAccount().getEmail());
    //         account.set_activated(true);
    //         accountRepository.save(account);
    //         modelAndView.setViewName("accountVerified");
    //     }
    //     else
    //     {
    //         modelAndView.addObject("message","The link is invalid or broken!");
    //         modelAndView.setViewName("error");
    //     }

    //     return modelAndView;
    // }

}
