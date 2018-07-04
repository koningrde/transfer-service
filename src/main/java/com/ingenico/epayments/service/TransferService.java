package com.ingenico.epayments.service;

import com.ingenico.epayments.domain.Account;
import com.ingenico.epayments.domain.Transfer;
import com.ingenico.epayments.repo.AccountRepository;
import com.ingenico.epayments.repo.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

@Service
@EnableTransactionManagement
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;


    public void cleanRepo() {
        // start with clean repos
        transferRepository.deleteAll();
        accountRepository.deleteAll();
    }




    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    public Account createMonetaryAccount(String name, Double initialBalance){
        Account newAccount = new Account(name, initialBalance);
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Account getMonetaryAccount(String name) {
        Account account = accountRepository.findByName(name);
        return account;
    }

    public Iterable<Account> allMonetaryAccounts() {
        return accountRepository.findAll();
    }

    public long countMonetaryAccounts() {
        return accountRepository.count();
    }


    public Transfer createTransfer(String fromAccountName, String toAccountName, double amount) {

        Account fromAccount = accountRepository.findByName(fromAccountName);
        Account toAccount = accountRepository.findByName(toAccountName);

        Transfer transfer = new Transfer(fromAccount, toAccount, amount);
        transferRepository.save(transfer);
        return transfer;
    }

    public Iterable<Transfer> allTransfers() {
        return transferRepository.findAll();
    }

    public long countTransfers() {
        return transferRepository.count();
    }

    public Transfer doTransfer(String fromAccountName, String toAccountName, double amount) {
        return doTransfer(fromAccountName, toAccountName, amount, 0);
    }

    // https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/transaction.html
    @Transactional
    public Transfer doTransfer(String fromAccountName, String toAccountName, double amount, long delay) {

        // check if it's an existing accounts
        Account fromAccount = accountRepository.findByName(fromAccountName);
        if (fromAccount == null)
            return null;

        // check if it's an existing account
        Account toAccount = accountRepository.findByName(toAccountName);
        if (toAccount == null)
            return null;

        // todo: acquire a global lock on fromAccount to avoid race condition


        // balance remains positive, so it's not overdrawn
        if (fromAccount.getBalance().compareTo(amount) >= 0) {

            // it would be nicer to use an aspect for testing purposes
            // delay this transaction on purpose to demonstrate the culprit
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // transfer the amount from one account to the other
            fromAccount.withdraw(amount);
            accountRepository.save(fromAccount);
            toAccount.deposit(amount);
            accountRepository.save(toAccount);

            return createTransfer(fromAccountName, toAccountName, amount);

        }
        return null;
    }


}
