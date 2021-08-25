package com.n47.phonebook.service;

import com.n47.phonebook.models.Client;
import com.n47.phonebook.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClientServiceImpl {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public List<Client> getClient(String name, String phoneNumber) {
       return clientRepository.findByPhoneNumberOrNameContainingIgnoreCase(phoneNumber,name);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
}
