package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.*;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Exceptions.ValidationException;
import com.example.SpringProject.Models.Author;
import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Models.User;
import com.example.SpringProject.Repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            Book book = modelMapper.map(createBookDTO, Book.class);
            List<Genre> genreList = genreService.create(createBookDTO.getGenres());
            Author author = authorService.getAuthor(createBookDTO.getAuthor());
            book.setPages(createBookDTO.getPages());
            book.setTitle(createBookDTO.getTitle());
            book.setAuthor(author);
            book.setGenres(genreList);
            for(Genre g: genreList){
                g.getBookSet().add(book);

            }
            author.getBookSet().add(book);
            repository.save(book);
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPages(book.getPages());

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

    public List<Book> getBooks(List<Long> bookIDs){
        List<Book> bookList = new ArrayList<>();
        for(Long id:bookIDs){
            if(repository.findById(id)!=null){
                bookList.add(repository.findById(id).get());
            }
            else throw new NotFoundException("Book doesnt exist");
        }
        return bookList;
    }

    public List<BookDTO> getBookDTOs(List<Book> books){
        List<BookDTO> bookList = new ArrayList<>();
        for(Book book:books){
            BookDTO bookDTO = new BookDTO()    ;
            bookDTO.setPages(book.getPages());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
        bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
        bookList.add(bookDTO);
        }
        return bookList;
    }

//    public List<BookDTO> getBooksByGenreID() {
//        return repository.findAll().stream().filter(book -> book.getGenres().contains()).map(book -> modelMapper.map(book, BookDTO.class)).toList();
//    }
}
