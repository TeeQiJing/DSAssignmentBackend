package com.wia1002.eGringottsBackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wia1002.eGringottsBackEnd.service.CurrencyService;
import com.wia1002.eGringottsBackEnd.model.Currency;
import com.wia1002.eGringottsBackEnd.repository.CurrencyRepository;

@RestController
@RequestMapping("currencyConversion")
@CrossOrigin
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @PostMapping("/addCurrency")
    public ResponseEntity<String> addCurrency(@RequestBody Currency currency) {
        currencyService.addCurrency(currency);
        return new ResponseEntity<>("Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/conversion/{currency1}/{currency2}/{amount}")
    public Double[] conversion(@PathVariable String currency1, @PathVariable String currency2, @PathVariable double amount) {
        return currencyService.conversion(currency1, currency2, amount);
    }

    @GetMapping("/printCurrency/{currency1}/{currency2}")
    public Double[] conversion(@PathVariable String currency1, @PathVariable String currency2) {
        return currencyService.printCurrency(currency1, currency2);
    }

    @GetMapping("/getAllCurrency")
    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }

    @DeleteMapping("/deleteCurrency/{number}")
    public ResponseEntity<Boolean> deleteCurrency(@PathVariable int number) {
        Boolean result = currencyService.deleteCurrency(number);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/unique-coins")
    public List<String> getUniqueCoins() {
        return currencyService.getUniqueCoins();
    }

    

}
