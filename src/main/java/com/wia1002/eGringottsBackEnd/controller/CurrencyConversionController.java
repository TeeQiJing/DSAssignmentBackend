package com.wia1002.eGringottsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.wia1002.eGringottsBackEnd.service.CurrencyConversionService;

import com.wia1002.eGringottsBackEnd.model.Currency;

@RestController
@RequestMapping("currencyConversion")
@CrossOrigin
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("/addCurrency")
    public ResponseEntity<String> addCurrency(@RequestBody Currency currency) {
        currencyConversionService.addCurrency(currency);
        return new ResponseEntity<>("Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
