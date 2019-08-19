package com.bank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Client;
import com.bank.error.TransactionFailed;
import com.bank.repository.ClientRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "CRUD Operations for Customer", description = "CRUD Operations for Customer")
public class ClientController {
    @Autowired
    private ClientRepository repository;

    @ApiOperation(value = "Save Banks Client")
    @PostMapping("/client")
    public Client createCustomer(@ApiParam(value = "Client parameters", required = true) @RequestBody Client client) {
        return repository.save(client);
    }

    @ApiOperation(value = "Update a client by client id")
    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateCustomer(
            @ApiParam(value = "Client ID", required = true) @PathVariable(value = "id") Long clientId,
            @ApiParam(value = "Client Name", required = true) @RequestParam String fullName) {
        Optional<Client> opt = repository.findById(clientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + clientId);
        }
        Client client = opt.get();
        client.setFullName(fullName);
        return ResponseEntity.ok(repository.save(client));
    }

    @ApiOperation(value = "Get the list of clients for this bank", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list") })
    @GetMapping("/clients")
    public List<Client> getClients() {
        return repository.findAll();
    }

    @ApiOperation(value = "Get bank's client by client Id")
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(
            @ApiParam(value = "Client ID", required = true) @PathVariable(value = "id") Long clientId) {
        Optional<Client> client = repository.findById(clientId);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(client.get());
        } else {
            throw new TransactionFailed("We do not have a matching client with given client id : " + clientId);
        }
    }

    @ApiOperation(value = "Delete a bank's client")
    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> delete(
            @ApiParam(value = "Client ID", required = true) @PathVariable(value = "id") Long clientId) {
        Optional<Client> opt = repository.findById(clientId);
        if (!opt.isPresent()) {
            throw new TransactionFailed("We do not have a matching client with given client id : " + clientId);
        }
        Client client = opt.get();

        repository.delete(client);

        return ResponseEntity.ok().build();
    }
}
