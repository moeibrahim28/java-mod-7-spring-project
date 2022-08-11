package com.example.SpringProject.Models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ReadingList> readingLists;



    @ManyToMany
    @JoinTable(name="BooksList",
    joinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genreList;
}
