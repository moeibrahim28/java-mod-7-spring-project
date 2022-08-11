package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.CreateUserDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Repositories.BookRepository;
import com.example.SpringProject.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public CreateBookDTO create(CreateBookDTO createBookDTO) {
        Book book = modelMapper.map(createBookDTO, Book.class);
        return modelMapper.map(repository.save(book), CreateBookDTO.class);
    }

    public List<BookDTO> getAll() {
        return repository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
    }
}
