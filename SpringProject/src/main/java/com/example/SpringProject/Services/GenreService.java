package com.example.SpringProject.Services;

import com.example.SpringProject.Repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;



}
