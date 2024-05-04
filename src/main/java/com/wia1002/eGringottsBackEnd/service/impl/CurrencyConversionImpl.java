package com.wia1002.eGringottsBackEnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.wia1002.eGringottsBackEnd.model.Coin;
import com.wia1002.eGringottsBackEnd.model.Graph;
import com.wia1002.eGringottsBackEnd.service.CurrencyConversionService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CurrencyConversionImpl implements CurrencyConversionService{
    
    @Autowired
    private List<Coin> coin;

    @Autowired
    private Graph<Coin, Double> graph;

    @Override
    public void printCurrency() {
        graph.print();
    }

    @Override
    public double conversion(String currency1, String currency2, double value) {
        return value;
    }

    @Override
    public void addCurrency(String name) {
        Coin newCoin = new Coin(name);
        coin.add(newCoin);
        graph.addVertex(newCoin);
    }

    @Override
    public void deductProcessingFee(double processingFee) {

    }

    @Override
    public void receipt() {

    }

}
