package com.ingenico.epayments.domain;

import javax.persistence.*;

/**
 * Transfer tracks payments of a certain amount, from one account to another account
 */

@Entity
@Table(name = "TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Account fromAccount;

    @ManyToOne
    @JoinColumn
    private Account toAccount;

    @Column
    private Double amount;


    protected Transfer() {
    }

    public Transfer(Account fromAccount, Account toAccount, Double amount) {
        if (amount < 0)
            throw new RuntimeException("Invalid negative transfer amount");

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                '}';
    }

}
