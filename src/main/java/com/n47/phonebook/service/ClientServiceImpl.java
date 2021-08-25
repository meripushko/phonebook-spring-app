package com.n47.phonebook.service;

import com.n47.phonebook.models.Client;
import com.n47.phonebook.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<Client> getClient(Client client) {
       Client clientSearchedByNumber = clientRepository.findByPhoneNumber(client.getPhoneNumber()).get();
       List<Client> clientsSearchedByName = clientRepository.findByNameContainingIgnoreCase(client.getName());
       clientsSearchedByName.add(clientSearchedByNumber);
       return clientsSearchedByName;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
}
