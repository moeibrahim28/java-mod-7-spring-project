package com.example.SpringProject.DTO;

import com.example.SpringProject.Models.ReadingList;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String username;
}
