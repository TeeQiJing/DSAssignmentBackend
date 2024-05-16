package com.wia1002.eGringottsBackEnd.service;

import java.util.List;

import com.wia1002.eGringottsBackEnd.model.Currency;

public interface CurrencyService {

    //show a bunch of currency and conversion value
    public Double[] printCurrency(String currency1, String currency2);

    //input 'from' and 'to' currencies along with their values to get the desired exchange based on the list of currencies provided before
    public Double[] conversion(String currency1, String currency2, double value);

    //add new currency
    public void addCurrency(Currency currency);

    //update currency
    public boolean deleteCurrency(int value);

    //update the balance in the user's account after deducting the processing fee
    public void deductProcessingFee(double processingFee);

    //include all relevant details about the transaction in the receipt
    public void receipt();

    public int getVertex(String str);

    public List<String> getUniqueCoins() ;
    
}
