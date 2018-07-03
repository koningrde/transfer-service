package com.ingenico.epayments.web;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    @NotNull
    private String name;

    @Min(0)
    private Double balance;


    protected AccountDTO() {
    }

    public AccountDTO(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
