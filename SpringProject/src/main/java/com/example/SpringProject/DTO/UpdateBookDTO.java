package com.example.SpringProject.DTO;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

@Data
public class UpdateBookDTO {
    private String title;
    private AuthorDTO author;
    private List<String> genres;
    @Min(1)
    private int pages;
}
