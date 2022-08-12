package com.example.SpringProject.Controllers;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.CreateUserDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Services.BookService;
import com.example.SpringProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BookService bookService;


    @PostMapping
    public BookDTO createBook(@Valid @RequestBody CreateBookDTO createBookDTO){
        return bookService.create(createBookDTO);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        if(bookService.getById(id)!=null){
            bookService.deleteBook(id);
        }
    }
}

