package com.example.SpringProject.Repositories;

import com.example.SpringProject.Models.Book;
import com.example.SpringProject.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
