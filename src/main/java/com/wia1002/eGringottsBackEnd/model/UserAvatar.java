package com.wia1002.eGringottsBackEnd.model;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Lob
    @Column(name = "image_path", length = Integer.MAX_VALUE)
    private byte[] image_path;


}
