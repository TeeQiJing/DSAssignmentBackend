package com.wia1002.eGringottsBackEnd.service;

import com.wia1002.eGringottsBackEnd.model.Currency;

public interface CurrencyConversionService {

    //show a bunch of currency and conversion value
    public void printCurrency();

    //input 'from' and 'to' currencies along with their values to get the desired exchange based on the list of currencies provided before
    public double conversion(String currency1, String currency2, double value);

    //add new currency
    public void addCurrency(Currency currency);

    //update the balance in the user's account after deducting the processing fee
    public void deductProcessingFee(double processingFee);

    //include all relevant details about the transaction in the receipt
    public void receipt();
    
}
