package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorDTO {
    @JsonProperty("author_name")
    private String authorName;
}
