package com.wia1002.eGringottsBackEnd.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "transaction") 
public class Transaction {
    

    /*@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator") */
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "sender_account_number")
    private String sender_account_number;

    @Transient
    private Account sender;

    @Transient
    private Account receiver;

    @Column(name = "receiver_account_number")
    private String receiver_account_number;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date_of_trans")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime date_of_trans;

    @Column(name = "category")
    private String category;

    @Column(name ="reference")
    private String reference;

    @Column(name ="current_balance")
    private double current_balance;

    public Transaction(double amount, String sender_account_number, String receiver_account_number, String category) {
        this.amount = amount;
        this.sender_account_number = sender_account_number;
        this.receiver_account_number = receiver_account_number;
        this.date_of_trans=LocalDateTime.now();
        this.category = category;
    }

    
    }

    


