package com.example.SpringProject.DTO;

import com.sun.istack.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BookDTO {
    private Long id;
    private AuthorDTO authorDTO;
    private String title;
    private int pages;
    private LocalDateTime published;
}
