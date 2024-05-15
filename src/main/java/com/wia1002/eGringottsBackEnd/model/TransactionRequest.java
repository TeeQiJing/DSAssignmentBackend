package com.wia1002.eGringottsBackEnd.model;

public class TransactionRequest {
    private double amount;
    private String sender_account_number;
    private String receiver_account_number;
    private String reference;
    private String category;

    // Constructor
    public TransactionRequest(double amount, String sender_account_number, String receiver_account_number, String reference, String category) {
        this.amount = amount;
        this.sender_account_number = sender_account_number;
        this.receiver_account_number = receiver_account_number;
        this.reference = reference;
        this.category = category;
    }

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSenderAccountNumber() {
        return sender_account_number;
    }

    public void setSenderAccountNumber(String sender_account_number) {
        this.sender_account_number = sender_account_number;
    }

    public String getReceiverAccountNumber() {
        return receiver_account_number;
    }

    public void setReceiverAccountNumber(String receiver_account_number) {
        this.receiver_account_number = receiver_account_number;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
