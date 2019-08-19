package com.bank.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bank.entity.Client;

@Service
@Transactional
public interface BankingOpsService {
    void transfer(Client fromClient, Client toClient, double amount);

    void deposit(Client client, double amount);

    void withdraw(Client client, double amount);

}