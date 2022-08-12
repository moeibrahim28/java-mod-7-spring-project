package com.example.SpringProject.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReadingListDTO {
    private Long id;
    private String name;
    private UserDTO user;
    private List<BookDTO> books;
}
