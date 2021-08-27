package com.n47.phonebook.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

// Client entity class, describing the clients in the database, containing the fields: ID, Name and PhoneNumber
// id: integer. Unique identifier (key).
// name: String. Describes the client's name
// phoneNumber: String. Unique value, describing the client's phone number
@Entity
@Data
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+3897[0156][0-9]{6}$")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
}
