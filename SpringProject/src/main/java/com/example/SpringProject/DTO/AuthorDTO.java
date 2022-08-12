package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthorDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String authorName;
}
