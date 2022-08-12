package com.example.SpringProject.Controllers;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.GenreDTO;
import com.example.SpringProject.Services.BookService;
import com.example.SpringProject.Services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;


    @GetMapping("/{id}/books")
    public List<BookDTO> getBook(@PathVariable Long id) {
        return genreService.getById(id);
    }
}
