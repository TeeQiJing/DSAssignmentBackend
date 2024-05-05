package com.wia1002.eGringottsBackEnd.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wia1002.eGringottsBackEnd.model.Transaction;

public interface TransactionService {
  Transaction createTransaction(Transaction transaction);

  List<Transaction> getAllTransaction(String account_number);

  List<Transaction> getTransactionByCategory(String account_number, String category);

  List<Transaction> getTransactionsByDate(String account_number, LocalDate date_of_trans);

  List<Transaction> getTransactionsBetweenDates(String account_number, LocalDateTime startDate, LocalDateTime endDate);
}
