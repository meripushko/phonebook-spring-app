package com.n47.phonebook.service;

import com.n47.phonebook.models.Client;
import com.n47.phonebook.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
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

    public List<Client> getClient(String name, String phoneNumber) {
       if(name == null || name.isEmpty()){

           // If both name and phoneNumber parameters are not provided, return NULL, and handle on upper layer
           if(phoneNumber == null || phoneNumber.isEmpty()){
               return null;
           }

           return Arrays.asList(clientRepository.findByPhoneNumber(phoneNumber));

       }else{
           return clientRepository.findByPhoneNumberOrNameContainingIgnoreCase(phoneNumber,name);
       }
    }

    public Client createClient(Client client) {
        Client duplicateNumber = clientRepository.findByPhoneNumber(client.getPhoneNumber());

        // If the number provided by request is already present, return NULL, and handle on upper layer
        if(duplicateNumber != null) {
            return null;
        }
        return clientRepository.save(client);
    }
}
