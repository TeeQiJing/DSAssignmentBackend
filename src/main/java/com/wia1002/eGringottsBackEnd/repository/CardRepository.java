package com.wia1002.eGringottsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wia1002.eGringottsBackEnd.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    @Query("SELECT c FROM Card c WHERE c.account_number = :value")
    Card findByAccountNumber(String value);

    
} 
