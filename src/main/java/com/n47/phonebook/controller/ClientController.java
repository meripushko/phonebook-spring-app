package com.n47.phonebook.controller;

import com.n47.phonebook.models.Client;
import com.n47.phonebook.service.ClientServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/phonebook")
@Validated
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
    public List<Client> getSpecifiedClients(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "phoneNumber",required = false) String phoneNumber){
        return clientService.getClient(name, phoneNumber);
    }

    @PostMapping("/contacts")
    public Client createClient(@Valid @RequestBody Client client) {
        return clientService.createClient(client);
    }
}
