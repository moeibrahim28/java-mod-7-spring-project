package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.*;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Models.ReadingList;
import com.example.SpringProject.Models.User;
import com.example.SpringProject.Repositories.ReadingListRepository;
import com.example.SpringProject.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadingListService {

    @Autowired
    private ReadingListRepository readingListRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    public ReadingListDTO create(CreateUserReadingListDTO createUserReadingListDTO) {
        ReadingList readingList = new ReadingList();
        User user = getUser(createUserReadingListDTO.getUser());
        readingList.setBookList(bookService.getBooks(createUserReadingListDTO.getBookIDs()));
        readingList.setUser(user);
        readingList.setId(createUserReadingListDTO.getId());
        readingList.setName(createUserReadingListDTO.getName());
        user.getReadingLists().add(readingList);
        readingListRepository.save(readingList);
        ReadingListDTO readingListDTO = new ReadingListDTO();
        readingListDTO.setBooks(bookService.getBookDTOs(bookService.getBooks(createUserReadingListDTO.getBookIDs())));
        readingListDTO.setUser(createUserReadingListDTO.getUser());
        readingListDTO.setId(createUserReadingListDTO.getId());
        readingListDTO.setName(createUserReadingListDTO.getName());
        return readingListDTO;
    }

    public User getUser(UserDTO userDTO){
        if(!userRepository.existsById(userDTO.getId())){
            throw new NotFoundException("User doesnt exist");
        }
        else{
            User user= userRepository.findById(userDTO.getId()).get();
            return user;
        }
    }

    public UserDTO getUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public ReadingListDTO getById(Long id,Long list_id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        User user = getUser(userDTO);
        ReadingListDTO readingListDTO = new ReadingListDTO();
        readingListDTO.setBooks(bookService.getBookDTOs(user.getReadingLists().get((int) (list_id-1)).getBookList()));
        readingListDTO.setId(user.getReadingLists().get((int) (list_id-1)).getId());
        readingListDTO.setUser(getUserDTO(user));
        readingListDTO.setName(user.getReadingLists().get((int) (list_id-1)).getName());
        return readingListDTO;
    }

    public List<ReadingListDTO> getAll(Long id) {
        List<ReadingListDTO> readingListDTOList = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        User user = getUser(userDTO);
        for(int i = 0; i<user.getReadingLists().size();i++){
            ReadingListDTO readingListDTO = new ReadingListDTO();
            readingListDTO.setBooks(bookService.getBookDTOs(user.getReadingLists().get(i).getBookList()));
            readingListDTO.setId(user.getReadingLists().get(i).getId());
            readingListDTO.setUser(getUserDTO(user));
            readingListDTO.setName(user.getReadingLists().get(i).getName());
            readingListDTOList.add(readingListDTO);
        }
        return readingListDTOList;
    }
}
