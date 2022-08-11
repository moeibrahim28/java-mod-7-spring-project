package com.example.SpringProject.DTO;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserDTO {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    @Length(min = 4)
    private String password;
}
