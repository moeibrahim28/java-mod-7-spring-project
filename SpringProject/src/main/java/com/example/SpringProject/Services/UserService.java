package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.CreateUserDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Models.BugUser;
import com.example.SpringProject.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO create(CreateUserDTO createUserDTO) {
        BugUser user = modelMapper.map(createUserDTO, BugUser.class);
        return modelMapper.map(repository.save(user), UserDTO.class);
    }

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }
}
