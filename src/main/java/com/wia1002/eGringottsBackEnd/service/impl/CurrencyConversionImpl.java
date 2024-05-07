package com.wia1002.eGringottsBackEnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// import com.wia1002.eGringottsBackEnd.model.Coin;
import com.wia1002.eGringottsBackEnd.model.Graph;
import com.wia1002.eGringottsBackEnd.repository.CurrencyRepository;
import com.wia1002.eGringottsBackEnd.service.CurrencyConversionService;

import com.wia1002.eGringottsBackEnd.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Service
public class CurrencyConversionImpl implements CurrencyConversionService{
    
    @Autowired
    private Graph<String, Double> graph;

    @Autowired
    private Currency currency;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void printCurrency() {
        graph.print();
    }

    @Override
    public double conversion(String currency1, String currency2, double value) {
        return value;
    }

    @Override
    public void addCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    @Override
    public void deductProcessingFee(double processingFee) {

    }

    @Override
    public void receipt() {

    }

}
