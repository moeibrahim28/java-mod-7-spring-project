package com.example.SpringProject.DTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateBookDTO {
    @NotNull
    @NotBlank
    private String title;
    private AuthorDTO author;
    @Size(min = 1)
    private List<String> genres;
    @Min(1)
    private int pages;
}
