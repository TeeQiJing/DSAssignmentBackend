package com.wia1002.eGringottsBackEnd.service;


import com.wia1002.eGringottsBackEnd.model.Card;

public interface CardService {
    Card createCard(Card card);
    Card getCardById(String account_number);
}
