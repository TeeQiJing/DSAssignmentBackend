package com.wia1002.eGringottsBackEnd.model;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;


import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account") 
public class Account {

    @Id
    @Column(name = "account_number")
    private String account_number;

    // Log in using username & password
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    // Other details
    @Column(name = "dob")
    private String dob;
    @Column(name = "address")
    private String address;
    @Column(name = "mobile", unique = true)
    private String mobile;

    @NaturalId(mutable = true)
    @Column(name = "email", unique = true)
    private String email;

    // Transaction limit per day 
    @Column(name = "trans_limit")
    private double trans_limit;

    @Column(name = "interest_rate")
    private double interest_rate;

    @Column(name = "balance")
    private double balance;

    @Column(name = "initial_balance")
    private double initial_balance;

    @Column(name = "account_type")
    private String account_type;

    @Column(name = "signin_date")
    private String signin_date;

    @Column(name = "register_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime register_date;

    @Transient
    private Card card;

    @Transient
    private UserAvatar user_avatar;

    // allow user to enter password after
    // confirming the secure phrase during login
    @Column(name = "secure_phrase")
    private String secure_phrase;


    @Column(name = "is_enabled")
    private boolean isEnabled;

   

    public static String time(){
        LocalDateTime localDateTime=LocalDateTime.now();
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted=localDateTime.format(format);
        return formatted;
    }


    

    

   

    

}
