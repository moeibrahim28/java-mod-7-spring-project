package com.example.SpringProject.DTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateBookDTO {
    private Long id;
    private String title;
    private AuthorDTO author;
    private List<String> genres;
    @Min(1)
    private int pages;

}
