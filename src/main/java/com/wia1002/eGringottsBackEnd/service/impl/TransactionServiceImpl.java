package com.wia1002.eGringottsBackEnd.service.impl;

import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Transaction;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.TransactionRepository;
import com.wia1002.eGringottsBackEnd.service.TransactionService;
import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Account sender = accountServiceImpl.getAccountById(transaction.getSender_account_number());
        Account receiver = accountServiceImpl.getAccountById(transaction.getReceiver_account_number());
        if (sender.getTrans_limit() >= transaction.getAmount()
                && sender.getTrans_limit() > 0
                && sender.getBalance() >= transaction.getAmount()) {
                    System.out.println("gergegeb                    ");

            sender.setTrans_limit(sender.getTrans_limit() - transaction.getAmount());
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            receiver.setBalance(receiver.getBalance() + transaction.getAmount());
            accountRepository.save(sender);
            accountRepository.save(receiver);
            if (transaction.getDate_of_trans() == null)
                transaction.setDate_of_trans(LocalDateTime.now());
            Transaction savedTransaction = transactionRepository.save(transaction);
            return savedTransaction;
        } else
            throw new ResourceNotFoundException("transaction failed");

    }

    @Override
    public List<Transaction> getAllTransaction(String account_number) {
        List<Transaction> transactions = transactionRepository.getTransactionsHistory(account_number);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionByCategory(String account_number, String category) {
        List<Transaction> transactions = transactionRepository.getTransactionsHistory_Category(account_number,
                category);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByDate(String account_number, LocalDate date_of_trans) {
        List<Transaction> transactions = transactionRepository.getTransactionsHistory_Date(account_number,
                date_of_trans);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsBetweenDates(String account_number, LocalDateTime startDate,
            LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.getTransactionsBetweenDates(account_number, startDate,
                endDate);
        return transactions;

    }

}