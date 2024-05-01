package com.wia1002.eGringottsBackEnd.model;

import jakarta.persistence.Column;

// import java.time.LocalDate;

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
@Table(name = "card") 
public class Card {

    // Card Num as the primary key of the table
    
    @Column(name = "card_num")
    private String card_num;
    
    // Account Number as the foreign key of the table
  
    @Id
    @Column(name = "account_number")
    private String account_number;

    // Credit / Debit
    @Column(name = "card_type")
    private String card_type;

    // 6-digits pin 
    @Column(name = "card_pin")
    private String card_pin;

    // 3-digits cvv
    @Column(name = "cvv")
    private String cvv;

    // format in MM/yy
    @Column(name = "expiry_date")
    private String expiry_date;

    // Use SimpleDateFormat to convert 
    /** Example
     * 
    import java.text.SimpleDateFormat;
    import java.util.Date;
    public class SimpleDateFormatExample {
        public static void main(String[] args) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
            String strDate= formatter.format(date);
            System.out.println(strDate);
        }
    }
    */


    
}
