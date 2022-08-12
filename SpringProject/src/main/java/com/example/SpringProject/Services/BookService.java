package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.*;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Exceptions.ValidationException;
import com.example.SpringProject.Models.Author;
import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ModelMapper modelMapper;

    public BookDTO create(CreateBookDTO createBookDTO) {
        if (!repository.existsById(createBookDTO.getId())) {
            Book book = new Book();
            book.setPages(createBookDTO.getPages());
            book.setTitle(createBookDTO.getTitle());
            book.setAuthor(authorService.getAuthor(authorService.create(createBookDTO.getAuthor())));
            book.setGenres(genreService.getGenre(genreService.create(createBookDTO.getGenres())));
            BookDTO bookDTO = new BookDTO();
            bookDTO.setPages(book.getPages());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthorName(book.getAuthor().getName());
            Set<GenreDTO> genreDTOSet = new HashSet<>();
            for(Genre genre : book.getGenres()){

                GenreDTO genreDTO = new GenreDTO();
                genreDTO.setGenreName(genre.getName());
            genreDTOSet.add(genreDTO);}
            bookDTO.setGenreSet(genreDTOSet);
            repository.save(book);
            return bookDTO;
        } else throw new ValidationException("Book already exists");
    }

    public List<BookDTO> getAll() {
        return repository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
    }

    public BookDTO getById(Long id) {
        BookDTO bookDTO = repository
                .findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class)).get();
//       .orElseThrow(() -> new ChangeSetPersister.NotFoundException("Book not found"));
        return bookDTO;
    }

    public void deleteBook(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Book not found");
        }
    }

//    public List<BookDTO> getBooksByGenreID() {
//        return repository.findAll().stream().filter(book -> book.getGenreList().get()).map(book -> modelMapper.map(book, BookDTO.class)).toList();
//    }
}
