package com.n47.phonebook.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n47.phonebook.controller.ClientController;
import com.n47.phonebook.exceptions.ApiError;
import com.n47.phonebook.models.Client;
import com.n47.phonebook.models.dto.ClientDTO;
import com.n47.phonebook.service.ClientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientServiceImpl clientService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    private Client client;

    private String URI = "/phonebook/contacts";

    @BeforeEach
    void setUp() {
        client = new Client();
    }


    @Test
    void createValidClientTest() throws Exception {
        client.setName("Simeon");
        client.setPhoneNumber("+38975222333");
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        Mockito.when(clientService.createClient(Mockito.any(Client.class))).thenReturn(client);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String clientJson = result.getResponse().getContentAsString();
        Assertions.assertThat(clientJson).isNotEmpty();
        Assertions.assertThat(clientJson).isEqualToIgnoringCase(mapper.writeValueAsString(clientDTO));
    }

    @Test
    void nullNameCreatingTest() throws Exception {
        ApiError apiError = new ApiError("name");
        client.setName(null);
        client.setPhoneNumber("+38975222333");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }

    @Test
    void emptyNameCreatingTest() throws Exception {
        ApiError apiError = new ApiError("name");
        client.setName("");
        client.setPhoneNumber("+38975222333");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }

    @Test
    void nullNumberCreatingTest() throws Exception {
        ApiError apiError = new ApiError("phoneNumber");
        client.setName("Simeon");
        client.setPhoneNumber(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }

    @Test
    void emptyNumberCreatingTest() throws Exception {
        ApiError apiError = new ApiError("phoneNumber");
        client.setName("Simeon");
        client.setPhoneNumber("");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }

    @Test
    void invalidFormatNumberCreatingTest() throws Exception {
        ApiError apiError = new ApiError("phoneNumber");
        client.setName("Simeon");
        client.setPhoneNumber("+123451");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }

    @Test
    void invalidNumberAndNameCreatingTest() throws Exception {
        ApiError apiError = new ApiError("phoneNumber, name");
        client.setName("");
        client.setPhoneNumber("+3211244");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String errorJson = result.getResponse().getContentAsString();
        Assertions.assertThat(errorJson).isNotEmpty();
        Assertions.assertThat(errorJson).isEqualToIgnoringCase(mapper.writeValueAsString(apiError));
    }
}