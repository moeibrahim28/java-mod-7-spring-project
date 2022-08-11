package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenreDTO {
    @JsonProperty("genre")
    private String genreName;
}
