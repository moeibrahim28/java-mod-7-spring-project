package com.example.SpringProject.DTO;

import com.example.SpringProject.Models.Genre;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class BookDTO {
    private String authorName;
    private String title;
    private int pages;
    private Set<GenreDTO> genreSet;

}
