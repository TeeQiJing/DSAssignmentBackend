package com.wia1002.eGringottsBackEnd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Graph graph;

    @Autowired
    private Currency currency;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Double[] printCurrency(String currency1, String currency2) {
        return graph.computeCurrency(currency1, currency2, 1);
    }

    @Override
    public Double[] conversion(String currency1, String currency2, double amount) {
        return graph.computeCurrency(currency1, currency2, amount);
    }

    @Override
    public void addCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    @Override
    public boolean deleteCurrency(int number) {
        Currency upCurrency = currencyRepository.findById(number).orElse(null);
        if (upCurrency != null) {
            currencyRepository.deleteById(number);
            return true;
        }
        return false;
    }

    @Override
    public void deductProcessingFee(double processingFee) {
    }

    @Override
    public void receipt() {

    }

    @Override
    public List<String> getUniqueCoins() {
        return currencyRepository.findUniqueCoins();
    }

    @Override
    public int getVertex(String str) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVertex'");
    }

}
