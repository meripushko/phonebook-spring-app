package com.n47.phonebook.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n47.phonebook.models.Client;
import com.n47.phonebook.models.dto.ClientDTO;
import com.n47.phonebook.repository.ClientRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientPersistenceTest {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientRepository clientRepository;

    private String URI = "/phonebook/contacts";


    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:8080/phonebook";
    }

    @Test
    void createClient() throws Exception {
        RequestSpecification request = RestAssured.given();

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Simeon");
        clientDTO.setPhoneNumber("+38975222333");


        request.header("Content-Type", "application/json");
        request.body(mapper.writeValueAsString(clientDTO));

        Response response = request.post("/contacts");

        int statusCode = response.getStatusCode();
        Assertions.assertEquals(statusCode, 201);
    }

    @Test
    void searchByNameAndNumberTest() throws Exception {
        ClientDTO clientDTO1 = new ClientDTO("Ferdinand","+38975111222");
        Client client1 = modelMapper.map(clientDTO1,Client.class);

        ClientDTO clientDTO2 = new ClientDTO("Andrea","+38975111223");
        Client client2 = modelMapper.map(clientDTO2,Client.class);

        ClientDTO clientDTO3 = new ClientDTO("Simeon","+38975222333");
        Client client3 = modelMapper.map(clientDTO3,Client.class);

        ClientDTO clientDTO4 = new ClientDTO("Bert","+38975123456");
        Client client4 = modelMapper.map(clientDTO4,Client.class);

        ClientDTO[] expectedClients = {clientDTO1,clientDTO2,clientDTO3};

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);
        clientRepository.save(client4);

        RequestSpecification request = RestAssured.given();
        request.queryParam("name", "and");
        request.queryParam("phoneNumber",client3.getPhoneNumber());

        Response response = request.get("/contacts");

        int statusCode = response.getStatusCode();
        String jsonClients = response.getBody().asString();
        ClientDTO[] responseClients = mapper.readValue(jsonClients,ClientDTO[].class);
        Assertions.assertEquals(statusCode, 200);
        Assertions.assertArrayEquals(expectedClients,responseClients);

    }
}
