package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateUserReadingListDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private UserDTO user;
    @Size(min = 1)
    private List<Long> bookIDs;
}
