package com.wia1002.eGringottsBackEnd.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wia1002.eGringottsBackEnd.model.Transaction;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        @Query("SELECT t FROM Transaction t WHERE t.sender_account_number = :account_number OR t.receiver_account_number = :account_number ORDER BY t.date_of_trans ASC")
        List<Transaction> getTransactionsHistory(@Param("account_number") String account_number);

        @Query("SELECT t FROM Transaction t WHERE ( t.sender_account_number = :accountNumber OR t.receiver_account_number = :accountNumber ) AND t.category = :category ORDER BY t.date_of_trans DESC")
        List<Transaction> getTransactionsHistory_Category(@Param("accountNumber") String account_number,
                        @Param("category") String category);

        @Query("SELECT t FROM Transaction t WHERE (t.sender_account_number = :accountNumber OR t.receiver_account_number = :accountNumber) "
                        +
                        "AND (YEAR(t.date_of_trans) = YEAR(:date) " +
                        "AND (MONTH(t.date_of_trans) = MONTH(:date) OR MONTH(:date) = 0) " +
                        "AND (DAY(t.date_of_trans) = DAY(:date) OR DAY(:date) = 0)) " +
                        "ORDER BY t.date_of_trans DESC")
        // @Query("SELECT t FROM Transaction t WHERE ( t.sender_account_number =
        // :accountNumber OR t.receiver_account_number = :accountNumber ) AND
        // t.date_of_trans LIKE :date% ORDER BY t.date_of_trans DESC")
        List<Transaction> getTransactionsHistory_Date(@Param("accountNumber") String account_number,
                        @Param("date") LocalDate date_of_trans);

        @Query("SELECT t FROM Transaction t WHERE (t.sender_account_number = :accountNumber OR t.receiver_account_number = :accountNumber) "
                        +
                        "AND (t.date_of_trans BETWEEN :startDate AND :endDate) " +
                        "ORDER BY t.date_of_trans DESC")
        List<Transaction> getTransactionsBetweenDates(@Param("accountNumber") String account_number,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        @Query("SELECT t "+
 "FROM Transaction t "+
 "WHERE t.category IN ('Food', 'Education', 'Game', 'Business', 'Others') "+
 "AND t.sender_account_number = :accountNumber") 
        List<Transaction> getPieData(@Param("accountNumber") String accountNumber);


        @Query("select t.transaction_id from Transaction t where DATE_FORMAT(t.date_of_trans, '%Y-%m-%d %H:%i:%s') = ?1")
        List<Long> findTransactionIdByDateOfTransString(String dateOfTransString);

        @Query("select t from Transaction t ORDER BY t.date_of_trans ASC")
        List<Transaction> getAllTransaction();

        @Query("SELECT t from Transaction t ORDER BY t.date_of_trans DESC")
        List<Transaction> getRecentTransaction();

        @Query("SELECT t FROM Transaction t WHERE t.sender_account_number = :account_number OR t.receiver_account_number = :account_number ORDER BY t.date_of_trans DESC")
        List<Transaction> getRecentTransactionUser(@Param("account_number") String account_number);
}
