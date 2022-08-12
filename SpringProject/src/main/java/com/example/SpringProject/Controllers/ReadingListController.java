package com.example.SpringProject.Controllers;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.CreateUserReadingListDTO;
import com.example.SpringProject.DTO.ReadingListDTO;
import com.example.SpringProject.Services.BookService;
import com.example.SpringProject.Services.ReadingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ReadingListController {

    @Autowired
    private ReadingListService readingListService;

    @PostMapping("/{id}/reading_lists")
    public ReadingListDTO createBook(@Valid @RequestBody CreateUserReadingListDTO createUserReadingListDTO){
        return readingListService.create(createUserReadingListDTO);
    }

    @GetMapping("/{id}/reading_lists/{list_id}")
    public ReadingListDTO getBook(@PathVariable Long id,@PathVariable Long list_id) {
        return readingListService.getById(id,list_id);
    }

    @GetMapping("/{id}/reading_lists")
    public List<ReadingListDTO> getAllReadingLists(@PathVariable Long id) {
        return readingListService.getAll(id);
    }

}
