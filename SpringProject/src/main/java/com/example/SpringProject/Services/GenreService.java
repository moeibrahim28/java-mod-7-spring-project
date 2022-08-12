package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.GenreDTO;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Repositories.BookRepository;
import com.example.SpringProject.Repositories.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GenreService {

    @Autowired
    private GenreRepository repository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private ModelMapper modelMapper;

    //creates genres and adds them to the repository if they don't already exist
    public List<Genre> create(List<String> genreNames) {
        List<Genre> genreList = new ArrayList<>();
        log.trace("inside create genre method");
        for (int i = 0; i < genreNames.size(); i++) {
            log.info("checking if genre exists");
            if (repository.findByName(genreNames.get(i)) == null) {
                log.info("genre not found in repository so application creates a new one");
                Genre genre = new Genre();
                genre.setName(genreNames.get(i));
                repository.save(genre);
                genreList.add(genre);
            } else {
                log.info("genre found in repository and is being returned");
                genreList.add(modelMapper.map(repository.findByName(genreNames.get(i)), Genre.class));
            }
        }
        return genreList;
    }

    //transforms list of genres to DTOs
    public List<GenreDTO> getGenre(List<Genre> genreList) {
        List<GenreDTO> genreSet = new ArrayList<>();
        for (Genre genre : genreList) {
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setGenre(genre.getName());
            genreSet.add(genreDTO);
        }
        log.info("returning list of genres as list of genreDTOs");
        return genreSet;
    }

    //returns all books with specified genre id
    public List<BookDTO> getById(Long id) {
        log.trace("get books by genre id");
        List<BookDTO> bookDTOList = repository
                .findById(id).orElseThrow(() -> new NotFoundException("Book not found")).getBookSet().stream()
                .map(book -> {
                    BookDTO bookDTO = new BookDTO();
                    bookDTO.getAuthor().setAuthorName(bookRepository.findById(book.getId()).get().getAuthor().getName());
                    bookDTO.getAuthor().setId(bookRepository.findById(book.getId()).get().getAuthor().getId());
                    bookDTO.setGenres(getGenre(book.getGenres()));
                    bookDTO.setTitle(book.getTitle());
                    bookDTO.setPages(book.getPages());
                    return bookDTO;
                }).toList();
        log.info("returning list of bookDTOs by genre id");
        return bookDTOList;
    }

    //clears empty genres from repository
    public void clearEmptyGenres() {
        log.info("attempting to clear empty genres");
        List<Genre> genreList = repository.findAll();
        for (Genre g : genreList) {
            if (g.getBookSet().size() == 0) {
                log.info("empty genre dofound and is being deleted");
                repository.delete(g);
            }
        }
    }

}
