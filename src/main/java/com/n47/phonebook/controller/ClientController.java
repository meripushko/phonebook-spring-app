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

    // Used for simple JSON<->POJO conversion
    private final ModelMapper modelMapper;

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    // Index endpoint. When called, returns a list of all contacts persisted in the database
    @GetMapping("/")
    public List<ClientDTO> getAll(){
        return clientService.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    // Endpoint for searching for contacts either by NAME, PHONE NUMBER or BOTH.
    // Parameter: name (String, NOT REQUIRED)
    // Parameter: phoneNumber (String, NOT REQUIRED)
    //
    // Returns a list of clients that contain the name query parameter in their name (if provided)
    // and the client that has the phone number specified in the query parameter (if provided)
    @GetMapping("/contacts")
    public ResponseEntity<Object> getSpecifiedClients(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "phoneNumber",required = false) String phoneNumber){
        List<Client> clients = clientService.getClient(name, phoneNumber);
        if(clients == null){
            ApiError apiError = new ApiError("Must provide either name or phone number");
            return ResponseEntity.badRequest().body(apiError);
        }
        return ResponseEntity.ok(clients
                .stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList()));
    }

    // Endpoint for creating and persisting a client
    // Parameter: JSON body with two attributes: name, phoneNumber (both Strings)
    //
    // Constraints: name must not be a NULL value or a BLANK string
    // Constraints: phoneNumber must not be NULL or BLANK, must be a unique value and follow the pattern below:
    // phoneNumber Pattern: +3897[0,1,5 or 6]XXXXXX (any digit replaces X)
    // Example phoneNumber: +38971256321
    //
    // In case of error, returns a response with status BAD_REQUEST, with a JSON body describing the cause
    // In case of success, returns a response with status CREATED, with a JSON body of the saved client
    @PostMapping("/contacts")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDTO clientDTO) {

        Client client = modelMapper.map(clientDTO, Client.class);

        Client result = clientService.createClient(client);

        if(result == null){
            ApiError apiError = new ApiError("Phone number already exists");
            return ResponseEntity.badRequest().body(apiError);
        }

        ClientDTO response = modelMapper.map(result, ClientDTO.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
