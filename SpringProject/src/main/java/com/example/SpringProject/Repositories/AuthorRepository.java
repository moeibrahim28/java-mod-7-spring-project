package com.example.SpringProject.Repositories;

import com.example.SpringProject.Models.Author;
import com.example.SpringProject.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}