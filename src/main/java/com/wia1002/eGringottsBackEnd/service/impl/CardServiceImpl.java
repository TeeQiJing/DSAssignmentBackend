package com.wia1002.eGringottsBackEnd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wia1002.eGringottsBackEnd.exception.ResourceNotFoundException;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.repository.CardRepository;
import com.wia1002.eGringottsBackEnd.service.CardService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService{
    @Autowired
    private CardRepository cardRepository;


    @Override
    public Card getCardById(String account_number){
        Card card = cardRepository.findById(account_number).orElseThrow(
            () -> new RuntimeException("Card is not exist with given Account Number : " + account_number)
        );


        return card;
    }

    @Override
    public Card createCard(Card card) {
        Card savedCard = cardRepository.save(card);
        return savedCard;
        
    }

}
