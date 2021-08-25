package com.n47.phonebook.repository;

import com.n47.phonebook.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByPhoneNumberOrNameContainingIgnoreCase(String phoneNumber, String name);
}
