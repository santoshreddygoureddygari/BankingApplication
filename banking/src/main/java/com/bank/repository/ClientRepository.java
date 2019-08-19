package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}