package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.GenreDTO;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Repositories.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;


    @Autowired
    private ModelMapper modelMapper;

    public Set<GenreDTO> create(List<String> genreNames) {
        Set<GenreDTO> genreDTOSet = new HashSet<>();
        for(String g : genreNames) {
            if (repository.findByName(g) == null) {
                Genre genre = new Genre();
                genre.setName(g);
                repository.save(genre);
                GenreDTO genreDTO = new GenreDTO();
                genreDTO.setGenreName(genre.getName());
                genreDTOSet.add(genreDTO);
            } else {
                genreDTOSet.add(modelMapper.map(repository.findByName(g), GenreDTO.class));
            }
        }
        return genreDTOSet;
    }

    public Set<Genre> getGenre(Set<GenreDTO> genreList) {
        Set<Genre> genreSet = new HashSet<>();
        for(GenreDTO genre: genreList){
            Genre newGenre = new Genre();
            newGenre.setName(genre.getGenreName());
            genreSet.add(newGenre);
        }
        return genreSet;
    }

//    public List<BookDTO> getById(Long id) {
//        GenreDTO genreDTO = new GenreDTO();
//        genreDTO.setId(id);
//        List<BookDTO> bookDTOList = List.of(repository
//                .findById(id)
//                .map(book -> modelMapper.map(book,BookDTO.class))
//                .filter(bookDTO -> bookDTO.getGenreList().contains(genreDTO)).orElseThrow(() -> new NotFoundException("Book not found")));
//        return bookDTOList;
//    }

}
