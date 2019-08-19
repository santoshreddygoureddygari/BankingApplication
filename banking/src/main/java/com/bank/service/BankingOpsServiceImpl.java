package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.BankAccount;
import com.bank.entity.Client;
import com.bank.error.TransactionFailed;
import com.bank.repository.ClientRepository;

@Service
public class BankingOpsServiceImpl implements BankingOpsService {
    @Autowired
    private ClientRepository repository;

    @Override
    public void transfer(Client fromClient, Client toClient, double amount) {
        this.withdraw(fromClient, amount);
        this.deposit(toClient, amount);
    }

    @Override
    public void deposit(Client client, double amount) {
        BankAccount bankAccount = client.getBankAccount();
        validateAmount(amount);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        repository.save(client);
    }

    private void validateAmount(double amount) {
        if (amount < 0) {
            throw new TransactionFailed("Provide correct balance to proceed");
        }
    }

    @Override
    public void withdraw(Client client, double amount) {
        BankAccount bankAccount = client.getBankAccount();
        validateAmount(amount);
        double balance = bankAccount.getBalance();
        balance -= amount;
        validateAmount(balance);
        bankAccount.setBalance(balance);
        repository.save(client);
    }
}