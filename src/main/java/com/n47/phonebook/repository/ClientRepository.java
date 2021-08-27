package com.n47.phonebook.repository;

import com.n47.phonebook.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring Data JPA repository for the Client entity used for persisting client objects to the Database.
// Two additional methods provided
// findByPhoneNumber: Returns a client with the provided unique phone number or NULL if none is provided
// findByPhoneNumberOrNameContainingIgnoreCase: Searches for clients that contain the parameter NAME in their names
// and for the client with the phone Number matching the parameter PHONENUMBER.
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByPhoneNumber(String phoneNumber);
    List<Client> findByPhoneNumberOrNameContainingIgnoreCase(String phoneNumber, String name);
}
