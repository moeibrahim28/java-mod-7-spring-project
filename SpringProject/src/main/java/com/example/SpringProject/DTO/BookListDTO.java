package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class BookListDTO {
    @JsonProperty("readingList_id")
    private Long id;
    private List<BookDTO> bookDTOList;
}
