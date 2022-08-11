package com.example.SpringProject.Repositories;

import com.example.SpringProject.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
}
