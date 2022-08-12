package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.GenreDTO;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Repositories.BookRepository;
import com.example.SpringProject.Repositories.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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

        for(int i =0;i<genreNames.size();i++) {
            if (repository.findByName(genreNames.get(i))==null) {
                Genre genre = new Genre();
                genre.setName(genreNames.get(i));
                repository.save(genre);
                genreList.add(genre);
            } else {
                genreList.add(modelMapper.map(repository.findByName(genreNames.get(i)), Genre.class));
            }
        }
        return genreList;
    }

    //transforms list of genres to DTOs
    public List<GenreDTO> getGenre(List<Genre> genreList) {
        List<GenreDTO> genreSet = new ArrayList<>();
        for(Genre genre: genreList){
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setGenre(genre.getName());
            genreSet.add(genreDTO);
        }
        return genreSet;
    }

    public void updateGenre(Genre genre){
        repository.save(genre);
    }

    //returns all books with specified genre id
    public List<BookDTO> getById(Long id) {
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
        return bookDTOList;
    }

    //clears empty genres from repository
    public void clearEmptyGenres(){
        List<Genre> genreList = repository.findAll();
        for(Genre g : genreList){
            if(g.getBookSet().size()==0){
                repository.delete(g);
            }
        }
    }

}
