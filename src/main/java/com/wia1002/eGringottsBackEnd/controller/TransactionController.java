package com.wia1002.eGringottsBackEnd.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.wia1002.eGringottsBackEnd.model.Transaction;
import com.wia1002.eGringottsBackEnd.model.TransactionRequest;
import com.wia1002.eGringottsBackEnd.repository.TransactionRepository;
import com.wia1002.eGringottsBackEnd.service.EmailService;
import com.wia1002.eGringottsBackEnd.service.TransactionService;
import com.wia1002.eGringottsBackEnd.service.impl.PdfService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;



import java.io.ByteArrayInputStream;
import java.io.IOException;


@RestController
// @AllArgsConstructor
@RequestMapping("transaction")
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;

   @PostMapping("/add")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        // Create a new Transaction object
        Transaction transaction = new Transaction();
        
        // Set the fields from the request
        // transaction.setAmount(transactionRequest.getAdd_amount());
        transaction.setSender_account_number(transactionRequest.getSenderAccountNumber());
        transaction.setReceiver_account_number(transactionRequest.getReceiverAccountNumber());
        transaction.setReference(transactionRequest.getReference());
        transaction.setCategory(transactionRequest.getCategory());
        
        // Call the service method to create the transaction
        transactionService.createTransaction(transaction, transactionRequest.getDeduct_amount(), transactionRequest.getAdd_amount());
        
        return new ResponseEntity<>("Transaction successfully", HttpStatus.CREATED);
    }
   @PostMapping("/topup")
    public ResponseEntity<String> topup(@RequestBody TransactionRequest transactionRequest) {
        // Create a new Transaction object
        Transaction transaction = new Transaction();
        
        // Set the fields from the request
        // transaction.setAmount(transactionRequest.getAdd_amount());
        transaction.setSender_account_number(transactionRequest.getSenderAccountNumber());
        transaction.setReceiver_account_number(transactionRequest.getReceiverAccountNumber());
        transaction.setReference(transactionRequest.getReference());
        transaction.setCategory(transactionRequest.getCategory());
        
        // Call the service method to create the transaction
        transactionService.createTransaction(transaction, transactionRequest.getDeduct_amount(), transactionRequest.getAdd_amount());
        
        return new ResponseEntity<>("Transaction successfully", HttpStatus.CREATED);
    }


    @GetMapping("/getTransactionHistory/{account_number}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @PathVariable("account_number") String account_number) {
        List<Transaction> transactions = transactionService.getAllTransaction(account_number);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getAllTransactionHistory")
    public ResponseEntity<List<Transaction>> getAllTransactionHistory() {
        List<Transaction> transactions = transactionRepository.getAllTransaction();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getRecentTransactionHistory")
    public ResponseEntity<List<Transaction>> getRecentTransactionHistory() {
        List<Transaction> transactions = transactionRepository.getRecentTransaction();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getRecentTransactionHistory/{account_number}")
    public ResponseEntity<List<Transaction>> getRecentTransactionHistoryUser(@PathVariable("account_number") String account_number) {
        List<Transaction> transactions = transactionRepository.getRecentTransactionUser(account_number);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getTransactionHistory/{account_number}/category/{category}")
        public ResponseEntity<List<Transaction>> getTransactionCategory(@PathVariable("account_number") String account_number,@PathVariable("category") String category){
        List<Transaction> transactions=transactionService.getTransactionByCategory(account_number,category);
        System.out.println(transactions.toString());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getTransactionHistory/{account_number}/date/{date}")
    public ResponseEntity<List<Transaction>> getTransactionDate(@PathVariable("account_number") String account_number,
            @PathVariable("date") String date) {
        LocalDate date_of_trans = ChangeFormat(date);
        List<Transaction> transactions = transactionService.getTransactionsByDate(account_number, date_of_trans);
        return ResponseEntity.ok(transactions);
    }

    LocalDate ChangeFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_of_trans = LocalDate.parse(date, formatter);
        return date_of_trans;
    }

    @GetMapping("/getTransactionHistory/{account_number}/date/{startDate}/{endDate}")
    public ResponseEntity<List<Transaction>> getTransactionBetweenDates(
            @PathVariable("account_number") String account_number,
            @PathVariable("startDate") String startDateString,
            @PathVariable("endDate") String endDateString) {
        LocalDateTime startDateTime = ChangeTimeFormat(startDateString, true);
        LocalDateTime endDateTime = ChangeTimeFormat(endDateString, false);
        List<Transaction> transactions = transactionService.getTransactionsBetweenDates(account_number, startDateTime,
                endDateTime);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getSpecificTransactionHistory/{transaction_id}")
    public ResponseEntity<Transaction> getSpecificTransaction(
            @PathVariable("transaction_id") Long transaction_id) {
     
        return ResponseEntity.ok((transactionRepository.findById(transaction_id)).get());
    }

    // LocalDateTime ChangeTimeFormat(String date, boolean startstatus) {
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //     if (startstatus) {
    //         date += " 00:00:00";
    //     } else {
    //         date += " 23:59:59";
    //     }
    //     LocalDateTime date_of_trans = LocalDateTime.parse(date, formatter);
    //     return date_of_trans;
    // }
    
    LocalDateTime ChangeTimeFormat(String date, boolean startstatus) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (startstatus) {
            date += " 00:00:00";
        } else {
            date += " 23:59:59";
        }
        LocalDateTime date_of_trans = LocalDateTime.parse(date, formatter);
        return date_of_trans;
    }

    @GetMapping("/getPie/{account_number}")
    public List<Transaction> getPie(
            @PathVariable("account_number") String account_number) {
        return transactionRepository.getPieData(account_number);

    }

    @GetMapping("/deductServiceFee/{account_number}")
    public void deductServiceFee(@PathVariable("account_number") String account_number) {
        

    }




    @PostMapping("/sendReceipt/{email}")
    public void sendReceipt(@RequestBody java.util.Map<String, Object> transactionData, @PathVariable("email") String email) throws IOException, MessagingException, DocumentException {
        String senderAccountNumber = (String) transactionData.get("sender_account_number");
        String receiverAccountNumber = (String) transactionData.get("receiver_account_number");
        double transferAmount = Double.parseDouble(transactionData.get("transfer_amount").toString());
        double serviceFee = Double.parseDouble(transactionData.get("service_fee").toString());
        double addedAmount = Double.parseDouble(transactionData.get("add_amount").toString());
        String reference = (String) transactionData.get("reference");
        String category = (String) transactionData.get("category");
        String senderUsername = (String) transactionData.get("sender_username");
        String receiverUsername = (String) transactionData.get("receiver_username");
        String datetime = (String) transactionData.get("datetime");
        String senderCurrencyType = (String) transactionData.get("sender_currency_type");
        String receiverCurrencyType = (String) transactionData.get("receiver_currency_type");

        ByteArrayInputStream pdfStream = pdfService.generateTransactionReceipt(
            senderAccountNumber, 
            receiverAccountNumber,
            transferAmount, 
            serviceFee,
            addedAmount, 
            reference, 
            category,
            senderUsername,
            receiverUsername,
            datetime,
            senderCurrencyType,
            receiverCurrencyType
        );

        // Replace "userData.email" with the actual email field from your user data
        emailService.sendTransactionReceipt(email, pdfStream);
    }
}
