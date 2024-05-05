package com.wia1002.eGringottsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.Card;
import com.wia1002.eGringottsBackEnd.repository.AccountRepository;
import com.wia1002.eGringottsBackEnd.repository.CardRepository;
import com.wia1002.eGringottsBackEnd.service.CardService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("card")
@CrossOrigin("http://localhost:3000")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @PostMapping("/add")
    Card add(@RequestBody Card newCard) {
        return cardRepository.save(newCard);
        
    }
}
