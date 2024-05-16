package com.wia1002.eGringottsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wia1002.eGringottsBackEnd.model.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Query("SELECT c.value, c.processingFee FROM Currency c WHERE c.sourceCoin = :sourceCoin AND c.destinationCoin = :destinationCoin")
    List<Double[]> getValueAndProcessingFee(@Param("sourceCoin") String sourceCoin, @Param("destinationCoin") String destinationCoin);


    @Query("SELECT DISTINCT c.destinationCoin FROM Currency c UNION SELECT DISTINCT c.sourceCoin FROM Currency c")
    List<String> findUniqueCoins();
}

