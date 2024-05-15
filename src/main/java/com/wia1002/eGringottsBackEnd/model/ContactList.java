package com.wia1002.eGringottsBackEnd.model;

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
@Table(name = "contact_list") 
public class ContactList {
    @Id
    @Column(name = "Contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Contact_id;
    @Column(name = "username")
    private String username;
    @Column(name= "contact_mobile")
    private String contact_mobile;
    @Column(name="user_created_name")
    private String user_created_name;
    @Column(name="account_number")
    private String account_number;
}

