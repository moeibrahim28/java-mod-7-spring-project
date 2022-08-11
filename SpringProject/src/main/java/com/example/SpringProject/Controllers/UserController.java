package com.example.SpringProject.Controllers;

import com.example.SpringProject.DTO.CreateUserDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        return userService.create(createUserDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }
}
