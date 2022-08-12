package com.example.SpringProject.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserDTO {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
}
