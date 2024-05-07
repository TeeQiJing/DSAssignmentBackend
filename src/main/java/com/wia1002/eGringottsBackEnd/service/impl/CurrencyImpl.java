package com.wia1002.eGringottsBackEnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wia1002.eGringottsBackEnd.model.Graph;
import com.wia1002.eGringottsBackEnd.repository.CurrencyRepository;
import com.wia1002.eGringottsBackEnd.service.CurrencyService;

import com.wia1002.eGringottsBackEnd.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Service
public class CurrencyImpl implements CurrencyService{
    
    @Autowired
    private Graph<String, Double> graph;

    @Autowired
    private Currency currency;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public List<Double[]> printCurrency(String currency1, String currency2) {
        return currencyRepository.getValueAndProcessingFee(currency1, currency2);
    }

    @Override
    public List<Double[]> conversion(String currency1, String currency2, double changeValue) {
        List<Double[]> result = currencyRepository.getValueAndProcessingFee(currency1, currency2);
        result.get(0)[0] *= changeValue;
        return result;
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
