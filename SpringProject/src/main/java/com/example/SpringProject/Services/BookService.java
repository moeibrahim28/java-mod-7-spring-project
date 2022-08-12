package com.example.SpringProject.Services;

import com.example.SpringProject.DTO.BookDTO;
import com.example.SpringProject.DTO.CreateBookDTO;
import com.example.SpringProject.DTO.UpdateBookDTO;
import com.example.SpringProject.Exceptions.NotFoundException;
import com.example.SpringProject.Exceptions.ValidationException;
import com.example.SpringProject.Models.Author;
import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Models.Genre;
import com.example.SpringProject.Models.ReadingList;
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
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReadingListService readingListService;

    @Autowired
    private ModelMapper modelMapper;

    //creates book and puts it in repository and displays bookDTO to user
    public BookDTO create(CreateBookDTO createBookDTO) {
        log.trace("inside create book method");
        if (!repository.existsById(createBookDTO.getId())) {
            log.info("book not found in repository so application creates a new one");
            Book book = modelMapper.map(createBookDTO, Book.class);
            List<Genre> genreList = genreService.create(createBookDTO.getGenres());
            Author author = authorService.getAuthor(createBookDTO.getAuthor());
            book.setPages(createBookDTO.getPages());
            book.setTitle(createBookDTO.getTitle());
            book.setAuthor(author);
            book.setGenres(genreList);
            for (Genre g : genreList) {
                g.getBookSet().add(book);

            }
            author.getBookSet().add(book);
            repository.save(book);
            log.info("book saved to repository");
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPages(book.getPages());

            return bookDTO;
        } else{
            log.info("book already exists to request is denied");
            throw new ValidationException("Book already exists");
        }
    }

    public List<BookDTO> getAll() {
        log.info("getting all books from repository");
        return repository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
    }

    public BookDTO getById(Long id) {
        log.info("getting book by its specific id");
        BookDTO bookDTO = repository
                .findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class)).orElseThrow(() -> new NotFoundException("Book not found"));
        return bookDTO;
    }

    //delete book from genres then deletes the actual book from repository
    public void deleteBook(Long id) {
        log.info("attempting to delete book if it exists");
        if (repository.existsById(id)) {
            log.info("book found in repository");
            Book book = repository.findById(id).get();
            List<Genre> genreList = genreRepository.findAll();
            for (Genre g : genreList) {
                if (g.getBookSet().contains(book)) {
                    log.info("book is being removed from each genre it belongs to");
                    g.getBookSet().remove(repository.findById(id).get());
                    if (g.getBookSet().size() == 0) {
                        genreRepository.delete(g);
                    }
                }
            }
            repository.deleteById(id);
        } else {
            log.info("book was not found in repository");
            throw new NotFoundException("Book not found");
        }
    }

    //creates list of books out of a list of bookIDs for reading lists
    public List<Book> getBooks(List<Long> bookIDs) {
        log.info("getting books by their ids repository");
        List<Book> bookList = new ArrayList<>();
        for (Long id : bookIDs) {
            if (repository.findById(id) != null) {
                bookList.add(repository.findById(id).get());
            } else {
                log.info("book did not exist");
                throw new NotFoundException("Book doesnt exist");
            }
        }
        return bookList;
    }

    //takes list of books and transforms them into a list of bookDTOs
    public List<BookDTO> getBookDTOs(List<Book> books) {
        List<BookDTO> bookList = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setPages(book.getPages());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
            bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
            bookList.add(bookDTO);
        }
        return bookList;
    }

    //put mapping method for updating a book by its ID
    public BookDTO updateBook(Long id, UpdateBookDTO updateBookDTO) {
        log.trace("inside update book method");
        if (repository.existsById(id)) {
            log.info("book found to be updated");
            List<Genre> genreList = genreService.create(updateBookDTO.getGenres());
            Author author = authorService.getAuthor(updateBookDTO.getAuthor());
            Book book = repository.findById(id).get();
            book.setPages(updateBookDTO.getPages());
            book.setTitle(updateBookDTO.getTitle());
            book.setAuthor(author);

            for (Genre g : book.getGenres()) {
                g.getBookSet().remove(book);
            }

            book.getGenres().clear();
            book.setGenres(genreList);
            for (Genre g : genreList) {
                g.getBookSet().add(book);

            }
            author.getBookSet().add(book);
            repository.save(book);
            log.info("book saved to repository");
            //check if any genres are now empty and delete them
            genreService.clearEmptyGenres();
            //dto creation for user to see result
            BookDTO bookDTO = new BookDTO();
            bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPages(book.getPages());
            log.info("bookDTO returned to user");
            return bookDTO;
        } else throw new ValidationException("Book already exists");
    }

    //return bookDTO for search by title
    public BookDTO getByTitle(String title) {
        BookDTO bookDTO = new BookDTO();
        log.info("attempting to find book by title");
        if (repository.findByTitle(title) != null) {
            log.info("book with given title found");
            Book book = repository.findByTitle(title);
            bookDTO.setAuthor(authorService.getAuthorDTO(book.getAuthor()));
            bookDTO.setGenres(genreService.getGenre(book.getGenres()));
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPages(book.getPages());
        } else {
            log.info("book with that title was not found");
            throw new NotFoundException("Book not found");
        }
        return bookDTO;
    }
}

