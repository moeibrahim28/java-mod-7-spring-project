package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.CreateUserReadingListDTO;
import com.example.SpringProject.DTO.ReadingListDTO;
import com.example.SpringProject.DTO.UserDTO;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Models.ReadingList;
import com.example.SpringProject.Models.User;
import com.example.SpringProject.Repositories.ReadingListRepository;
import com.example.SpringProject.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReadingListService {

    @Autowired
    private ReadingListRepository readingListRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    //takes createUserReadingListDTO object and creates a ReadingListDTO
    //saves reading list ot repository
    public ReadingListDTO create(CreateUserReadingListDTO createUserReadingListDTO) {
        log.trace("Inside create method for reading list");
        ReadingList readingList = new ReadingList();
        User user = getUser(createUserReadingListDTO.getUser());
        readingList.setBookList(bookService.getBooks(createUserReadingListDTO.getBookIDs()));
        readingList.setUser(user);
        readingList.setId(createUserReadingListDTO.getId());
        readingList.setName(createUserReadingListDTO.getName());
        user.getReadingLists().add(readingList);
        readingListRepository.save(readingList);
        log.info("reading list created and saved");
        ReadingListDTO readingListDTO = new ReadingListDTO();
        readingListDTO.setBooks(bookService.getBookDTOs(bookService.getBooks(createUserReadingListDTO.getBookIDs())));
        readingListDTO.setUser(createUserReadingListDTO.getUser());
        readingListDTO.setId(createUserReadingListDTO.getId());
        readingListDTO.setName(createUserReadingListDTO.getName());
        log.info("readinglistDTO returning to user");
        return readingListDTO;
    }

    //    transforms userDTO to user object
    public User getUser(UserDTO userDTO) {
        log.info("attempting to find user from dto");
        if (!userRepository.existsById(userDTO.getId())) {
            log.info("could not find user");
            throw new NotFoundException("User doesnt exist");
        } else {
            log.info("user found and being returned to application");
            User user = userRepository.findById(userDTO.getId()).get();
            return user;
        }
    }

    //    transforms user object to userDTO
    public UserDTO getUserDTO(User user) {
        log.info("turning user object to DTO");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    //returns readinglistDTO by using user_if and list_id
    public ReadingListDTO getById(Long id, Long list_id) {
        log.info("attempting to find user reading list");
        if (userRepository.existsById(id)) {
            log.info("user found");
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            User user = getUser(userDTO);
            if (readingListRepository.existsById(list_id)) {
                log.info("user reading list found and will be returned as DTO");
                ReadingListDTO readingListDTO = new ReadingListDTO();
                readingListDTO.setBooks(bookService.getBookDTOs(user.getReadingLists().get((int) (list_id - 1)).getBookList()));
                readingListDTO.setId(user.getReadingLists().get((int) (list_id - 1)).getId());
                readingListDTO.setUser(getUserDTO(user));
                readingListDTO.setName(user.getReadingLists().get((int) (list_id - 1)).getName());
                return readingListDTO;
            } else {
                log.info("reading list was not found");
                throw new NotFoundException("Reading list does not exist");
            }
        } else {
            log.info("user was not found");
            throw new NotFoundException("Reading list does not exist");
        }
    }


    //returns a list of readingListDTOs using userID
    public List<ReadingListDTO> getAll(Long id) {
        List<ReadingListDTO> readingListDTOList = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        User user = getUser(userDTO);
        if (readingListRepository.existsById(id)) {
            for (int i = 0; i < user.getReadingLists().size(); i++) {
                ReadingListDTO readingListDTO = new ReadingListDTO();
                readingListDTO.setBooks(bookService.getBookDTOs(user.getReadingLists().get(i).getBookList()));
                readingListDTO.setId(user.getReadingLists().get(i).getId());
                readingListDTO.setUser(getUserDTO(user));
                readingListDTO.setName(user.getReadingLists().get(i).getName());
                readingListDTOList.add(readingListDTO);
            }
            return readingListDTOList;
        } else {
            log.info("user was not found");
            throw new NotFoundException("Reading list does not exist");
        }
    }
}
