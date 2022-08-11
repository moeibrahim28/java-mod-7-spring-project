package com.example.SpringProject.Models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "ReadingLists")
@Getter
@Setter
public class ReadingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String name;

    @ManyToOne
    private BugUser user;

    @ManyToMany
    @JoinTable(name="ReadingLists",
            joinColumns = @JoinColumn(name = "user_username"),
            inverseJoinColumns = @JoinColumn(name = "book_title"))
    private List<Book> bookList;
}
