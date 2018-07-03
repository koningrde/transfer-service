package com.ingenico.epayments.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransferDTO {
    @NotNull
    private String fromAccount;

    @NotNull
    private String toAccount;

    @Min(10)
    private Double amount;


    protected TransferDTO() {
    }

    public TransferDTO(@NotNull String fromAccount, @NotNull String toAccount, @Min(10) Double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
