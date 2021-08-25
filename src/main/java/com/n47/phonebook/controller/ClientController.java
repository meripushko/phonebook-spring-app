package com.n47.phonebook.controller;

import com.n47.phonebook.models.Client;
import com.n47.phonebook.service.ClientServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/phonebook")
public class ClientController {
    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<Client> getAll(){
        return clientService.findAll();
    }

    @GetMapping("/contacts")
    public List<Client> getSpecifiedClients(@RequestBody Client client){
        return clientService.getClient(client);
    }

    @PostMapping("/contacts")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }
}
