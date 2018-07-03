package com.ingenico.epayments.domain;

import javax.persistence.*;

/**
 * Account has a name and holds the balance (in Euro's)
 */


@Entity
@Table(name="ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private Double balance;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name null");
        }

        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        if (balance < 0.0) {
            throw new IllegalArgumentException("Negative balance");
        }

        this.balance = balance;
    }

    protected Account() {
    }

    public Account(String name, Double balance) {
        setName(name);
        setBalance(balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Account) {

            return this.name.equalsIgnoreCase(((Account) obj).name);

        } else
            return super.equals(obj);
    }

    public void withdraw(double amount) {
        this.balance = this.balance - amount;
    }

    public void deposit(double amount) {
        this.balance = this.balance + amount;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}

