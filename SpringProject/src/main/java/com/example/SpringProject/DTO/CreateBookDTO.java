package com.example.SpringProject.DTO;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateBookDTO {
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private AuthorDTO authorDTO;
    @NotBlank
    @NotNull
    private GenreDTO genreDTO;
    @Min(1)
    private int pages;

}
