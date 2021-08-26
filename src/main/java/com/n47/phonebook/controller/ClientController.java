package com.n47.phonebook.controller;

import com.n47.phonebook.exceptions.ApiError;
import com.n47.phonebook.models.Client;
import com.n47.phonebook.models.dto.ClientDTO;
import com.n47.phonebook.service.ClientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phonebook")
@Validated
public class ClientController {

    private final ModelMapper modelMapper;

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public List<ClientDTO> getAll(){
        return clientService.findAll().stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/contacts")
    public List<ClientDTO> getSpecifiedClients(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "phoneNumber",required = false) String phoneNumber){
        return clientService.getClient(name, phoneNumber).stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/contacts")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDTO clientDTO) {

        Client client = modelMapper.map(clientDTO, Client.class);

        Client result = clientService.createClient(client);

        if(result == null){
            ApiError apiError = new ApiError("Phone number already exists");
            return ResponseEntity.badRequest().body(apiError);
        }

        ClientDTO response = modelMapper.map(result, ClientDTO.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
