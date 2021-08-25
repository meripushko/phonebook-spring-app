package com.n47.phonebook.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "^\\+3897[0156][0-9]{6}$")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
}
