package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class CreateUserReadingListDTO {
    private Long id;
    private String name;
    private UserDTO user;
    private List<Long> bookIDs;
}
