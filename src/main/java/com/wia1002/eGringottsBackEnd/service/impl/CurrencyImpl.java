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
import com.wia1002.eGringottsBackEnd.model.Vertex;

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
    public List<Double[]> printCurrency(String currency1, String currency2) {
        return currencyRepository.getValueAndProcessingFee(currency1, currency2);
    }

    @Override
    public List<Double[]> conversion(String currency1, String currency2, double changeValue) {
        List<Double[]> list = new ArrayList<>();
        graph.loadDatabase();
        list.add(graph.computeCurrency(currency1, currency2, changeValue));
        System.out.println(list.toString());
        // Double[] result = {changeValue*currencyRepository.getValueAndProcessingFee(currency1, currency2).get(0)[0], changeValue*currencyRepository.getValueAndProcessingFee(currency1, currency2).get(0)[1]};
        // list.add(result);
        return list;
    }

    @Override
    public int getVertex(String currency1) {
        try {
            return graph.add();
        }catch (Exception e) {
            return 100;
        }
        // return 100;
        // return graph.hasVertex(currency1);
        // return true;
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

}
