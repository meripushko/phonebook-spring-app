package com.n47.phonebook.service;

import com.n47.phonebook.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    List<Client> getClient(String name, String phoneNumber);

    Client createClient(Client client);
}
