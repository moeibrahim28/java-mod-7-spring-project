package com.example.SpringProject.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class ErrorsDTO {
    private Set<String> errors;
}