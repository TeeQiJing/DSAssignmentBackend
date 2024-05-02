package com.wia1002.eGringottsBackEnd.model;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTrans_limit() {
        return trans_limit;
    }

    public void setTrans_limit(double trans_limit) {
        this.trans_limit = trans_limit;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public UserAvatar getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(UserAvatar user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getSecure_phrase() {
        return secure_phrase;
    }

    public void setSecure_phrase(String secure_phrase) {
        this.secure_phrase = secure_phrase;
    }

    public Account(String account_number, String username, String password, String dob, String address, String mobile,
            String email, double balance, Card card,
            UserAvatar user_avatar, String secure_phrase) {
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
