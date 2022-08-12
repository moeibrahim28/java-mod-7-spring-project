package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.AuthorDTO;
import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.GenreDTO;
import com.example.SpringProject.Models.Author;
import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorDTO create(AuthorDTO authorDTO) {
        if (!repository.existsById(authorDTO.getId())) {
            Author author = new Author();
            author.setName(authorDTO.getAuthorName());
            repository.save(author);
            AuthorDTO newAuthorDTO = new AuthorDTO();
            newAuthorDTO.setAuthorName(author.getName());
            return newAuthorDTO;
        } else {
            return modelMapper.map(repository.existsById(authorDTO.getId()), AuthorDTO.class);
        }
    }

    public Author getAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getAuthorName());
        author.setId(authorDTO.getId());
        return author;
    }

    public AuthorDTO getAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorName(author.getName());
        authorDTO.setId(author.getId());
        return authorDTO;
    }

    public void updateAuthor(Author author){
        repository.save(author);
    }
}
