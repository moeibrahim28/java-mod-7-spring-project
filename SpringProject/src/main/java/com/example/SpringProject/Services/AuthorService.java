package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.AuthorDTO;
import com.example.SpringProject.Models.Author;
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

    //creates author from user input and returns dto
    //saves author to repository
    public AuthorDTO create(AuthorDTO authorDTO) {
        if (!repository.existsById(authorDTO.getId())) {
            Author author = new Author();
            author.setName(authorDTO.getAuthorName());
            repository.save(author);
            AuthorDTO newAuthorDTO = new AuthorDTO();
            newAuthorDTO.setAuthorName(author.getName());
            newAuthorDTO.setId(author.getId());
            return newAuthorDTO;
        } else {
            return modelMapper.map(repository.existsById(authorDTO.getId()), AuthorDTO.class);
        }
    }

    //transforms authorDTO to author object
    public Author getAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getAuthorName());
        author.setId(authorDTO.getId());
        return author;
    }

    //transforms author object to AuthorDTO
    public AuthorDTO getAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorName(author.getName());
        authorDTO.setId(author.getId());
        return authorDTO;
    }

}
