package com.n47.phonebook.service;

import com.n47.phonebook.models.Client;
import org.apache.tomcat.jni.User;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    List<Client> getClient(Client client);

    Client createClient(Client client);
}
