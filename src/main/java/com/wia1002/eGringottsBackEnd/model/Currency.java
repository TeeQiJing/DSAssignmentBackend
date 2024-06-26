package com.wia1002.eGringottsBackEnd.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Component
@Entity
@Table(name = "Currency")
public class Currency {

    @Id
    @GeneratedValue
    @Column(name = "No")
    private int number;

    @Column(name = "Source Coin")
    private String sourceCoin;

    @Column(name = "Destination Coin")
    private String destinationCoin;

    @Column(name = "Value")
    private double value;

    @Column(name = "Processing Fee")
    private double processingFee;

}
