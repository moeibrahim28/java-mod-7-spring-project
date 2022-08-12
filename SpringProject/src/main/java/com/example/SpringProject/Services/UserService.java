package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.CreateUserDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Models.User;
import com.example.SpringProject.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO create(CreateUserDTO createUserDTO) {
        log.info("creating user");
        User user = modelMapper.map(createUserDTO, User.class);
        log.info("user created");
        return modelMapper.map(repository.save(user), UserDTO.class);

    }

    //gets all users as userDTOs
    public List<UserDTO> getAll() {
        log.info("getting all users in the repository");
        return repository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    //delete user by id
    public void deleteById(Long id) {
        log.info("Attempting to delete user");
        if(repository.existsById(id)) {
            repository.deleteById(id);
            log.info("user exists and was deleted");
        }
        else {
            log.info("user did not exist and therefore could not be deleted");
            throw new NotFoundException("Activity not found");
        }
    }
}
