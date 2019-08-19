package com.bank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Client;
import com.bank.error.TransactionFailed;
import com.bank.repository.ClientRepository;
import com.bank.service.BankingOpsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/operation")
@Api(value = "Banking operations", description = "Bank operations for client")
public class BankingOpsController {

    @Autowired
    private BankingOpsService service;

    @Autowired
    private ClientRepository repository;

    @ApiOperation(value = "Add amount to client account")
    @PutMapping("/deposit/{clientId}")
    public void deposit(long clientId, double amount) {
        Optional<Client> opt = repository.findById(clientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + clientId);
        }
        Client client = opt.get();
        service.deposit(client, amount);
    }

    @ApiOperation(value = "Draw amount from client account")
    @PutMapping("/withdraw/{clientId}")
    public void withdraw(long clientId, double amount) {
        Optional<Client> opt = repository.findById(clientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + clientId);
        }
        Client client = opt.get();
        service.withdraw(client, amount);
    }

    @ApiOperation(value = "Transfer amount between 2 clients bank accounts")
    @PutMapping("/transfer/{fromClientId}/{toClientId}")
    public void transfer(long fromClientId, long toClientId, double amount) {
        Optional<Client> opt = repository.findById(fromClientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + fromClientId);
        }
        Client fromClient = opt.get();

        opt = repository.findById(toClientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + toClientId);
        }
        Client toClient = opt.get();
        service.transfer(fromClient, toClient, amount);
    }
}