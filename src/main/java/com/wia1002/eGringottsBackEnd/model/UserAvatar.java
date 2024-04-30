package com.wia1002.eGringottsBackEnd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_avatar") 
public class UserAvatar {
    @Id
    @Column(name = "account_number")
    private String account_number;

    @Column(name = "image_path")
    private String image_path;


}
