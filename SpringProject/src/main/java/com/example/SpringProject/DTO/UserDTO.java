package com.example.SpringProject.DTO;

import com.example.SpringProject.Models.ReadingList;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private List<ReadingList> readingLists;
}
