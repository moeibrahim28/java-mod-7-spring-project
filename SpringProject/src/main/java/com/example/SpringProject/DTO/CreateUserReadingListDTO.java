package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class CreateUserReadingListDTO {
    @JsonProperty("readingList")
    @NotNull
    private List<BookDTO> bookDTOList;
}
