package com.wia1002.eGringottsBackEnd.model;



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
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Transient
    private Card card;

    @Transient
    private UserAvatar user_avatar;

    // allow user to enter password after
    // confirming the secure phrase during login
    @Column(name = "secure_phrase")
    private String secure_phrase;

   

    

}
