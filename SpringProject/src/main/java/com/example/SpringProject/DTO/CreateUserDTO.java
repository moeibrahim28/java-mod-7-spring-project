package com.example.SpringProject.DTO;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
}
