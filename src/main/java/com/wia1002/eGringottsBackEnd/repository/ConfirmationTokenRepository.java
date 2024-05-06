package com.wia1002.eGringottsBackEnd.repository;

import org.hibernate.dialect.function.ConcatPipeFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wia1002.eGringottsBackEnd.model.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByAccountNumber(String accountNumber);
    
} 
