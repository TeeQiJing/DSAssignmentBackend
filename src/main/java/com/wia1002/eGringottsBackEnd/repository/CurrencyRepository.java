package com.wia1002.eGringottsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wia1002.eGringottsBackEnd.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Double> {

}

