package com.example.easyrecipes;

public class Transaction {
    private double amount;
    private String description;
    private boolean income;

    public Transaction(double amount, String description, boolean income) {
        this.amount = amount;
        this.description = description;
        this.income = income;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncome() {
        return income;
    }
}
