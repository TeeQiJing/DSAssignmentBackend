package com.wia1002.eGringottsBackEnd.model;




public class TransactionRequest {
    private double deduct_amount;
    private double add_amount;
    private String sender_account_number;
    private String receiver_account_number;
    private String reference;
    private String category;





    public TransactionRequest(double deduct_amount, double add_amount, String sender_account_number, String receiver_account_number, String reference, String category) {
        this.add_amount = add_amount;
        this.deduct_amount = deduct_amount;
        this.sender_account_number = sender_account_number;
        this.receiver_account_number = receiver_account_number;
        this.reference = reference;
        this.category = category;
    }

    // Getters and setters


    public String getSenderAccountNumber() {
        return sender_account_number;
    }

    public double getDeduct_amount() {
        return deduct_amount;
    }

    public void setDeduct_amount(double deduct_amount) {
        this.deduct_amount = deduct_amount;
    }

    public double getAdd_amount() {
        return add_amount;
    }

    public void setAdd_amount(double add_amount) {
        this.add_amount = add_amount;
    }

    public String getSender_account_number() {
        return sender_account_number;
    }

    public void setSender_account_number(String sender_account_number) {
        this.sender_account_number = sender_account_number;
    }

    public String getReceiver_account_number() {
        return receiver_account_number;
    }

    public void setReceiver_account_number(String receiver_account_number) {
        this.receiver_account_number = receiver_account_number;
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
