package com.wia1002.eGringottsBackEnd.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wia1002.eGringottsBackEnd.model.Transaction;
import com.wia1002.eGringottsBackEnd.service.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("transaction")
@CrossOrigin
public class TransactionController {
     private TransactionService transactionService;

        @PostMapping("/add")
        public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
            return new ResponseEntity<>("Transaction successfully",HttpStatus.CREATED);
        }

        @GetMapping("/getTransactionHistory/{account_number}")
        public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable("account_number") String account_number){
        List<Transaction> transactions =transactionService.getAllTransaction(account_number);
         return ResponseEntity.ok(transactions);
        }

    @GetMapping("/getTransactionHistory/{account_number}/category/{category}")
        public ResponseEntity<List<Transaction>> getTransactionCategory(@PathVariable("account_number") String account_number,@PathVariable("category") String category){
        List<Transaction> transactions=transactionService.getTransactionByCategory(account_number,category);
        System.out.println(transactions.toString());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getTransactionHistory/{account_number}/date/{date}")
    public ResponseEntity<List<Transaction>> getTransactionDate(@PathVariable("account_number") String account_number,@PathVariable("date") String date){
    LocalDate date_of_trans=ChangeFormat(date);
    List<Transaction> transactions=transactionService.getTransactionsByDate(account_number,date_of_trans);
        return ResponseEntity.ok(transactions);
}

   LocalDate ChangeFormat(String date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date_of_trans = LocalDate.parse(date, formatter); 
    return date_of_trans;
   }
   
   
   @GetMapping("/getTransactionHistory/{account_number}/date/{startDate}/{endDate}")
    public ResponseEntity<List<Transaction>> getTransactionBetweenDates
    (
        @PathVariable("account_number") String account_number,
        @PathVariable("startDate") String startDateString,
        @PathVariable("endDate") String endDateString
    )
    {
    LocalDateTime startDateTime=ChangeTimeFormat(startDateString,true);
    LocalDateTime endDateTime=ChangeTimeFormat(endDateString,false);
    List<Transaction> transactions=transactionService.getTransactionsBetweenDates(account_number,startDateTime,endDateTime);
        return ResponseEntity.ok(transactions);
}

LocalDateTime ChangeTimeFormat(String date,boolean startstatus){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    if(startstatus){
        date+=" 00:00:00";
    }
    else{
        date+=" 23:59:59";
    }
    LocalDateTime date_of_trans = LocalDateTime.parse(date, formatter); 
    return date_of_trans;
   }

}

