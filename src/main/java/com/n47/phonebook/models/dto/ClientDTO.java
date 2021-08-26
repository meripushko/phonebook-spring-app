package com.n47.phonebook.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ClientDTO {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+3897[0156][0-9]{6}$")
    private String phoneNumber;
}
