package com.example.SpringProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BookListDTO {
    @JsonProperty("readingList_id")
    private Long id;
    @Size(min = 1)
    private List<BookDTO> bookDTOList;
}
