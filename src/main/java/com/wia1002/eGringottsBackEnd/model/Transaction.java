package com.wia1002.eGringottsBackEnd.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Transaction(double amount,Account sender,Account receiver,LocalDateTime date,String category){
        if(sender.getTrans_limit() >= amount && sender.getTrans_limit() > 0 && sender.getBalance() >= amount){
            sender.setTrans_limit(sender.getTrans_limit() - amount);
            this.sender = sender;
            this.receiver = receiver;
            this.sender_account_number = sender.getAccount_number();
            this.receiver_account_number = receiver.getAccount_number();
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            
        }
        
        
        
        
    }
    
}
