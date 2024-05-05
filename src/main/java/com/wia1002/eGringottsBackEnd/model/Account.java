package com.wia1002.eGringottsBackEnd.model;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "account") 
public class Account {
    // Account Number as primary key of the table


    @Id
    @Column(name = "account_number")
    private String account_number;

    // Log in using username & password
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    // Other details
    @Column(name = "dob")
    private String dob;
    @Column(name = "address")
    private String address;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;

    // Transaction limit per day 
    @Column(name = "trans_limit")
    private double trans_limit;

    @Column(name = "interest_rate")
    private double interest_rate;

    @Column(name = "balance")
    private double balance;

    @Column(name = "account_type")
    private String account_type;

    @Column(name = "signin_date")
    private String signin_date;

    @Transient
    private Card card;

    @Transient
    private UserAvatar user_avatar;

    // allow user to enter password after
    // confirming the secure phrase during login
    @Column(name = "secure_phrase")
    private String secure_phrase;

    public Account(String account_number, String username, String password, String dob, String address, String mobile,
            String email, double balance, Card card,
            UserAvatar user_avatar, String secure_phrase) {
                System.out.println("Are you here             ");
        this.account_number = account_number;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.balance = balance;
        this.card = card;
        this.user_avatar = user_avatar;
        this.secure_phrase = secure_phrase;
    }

    public static String time(){
        LocalDateTime localDateTime=LocalDateTime.now();
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted=localDateTime.format(format);
        return formatted;
    }


    

    

   

    

}
