package com.example.SpringProject.Models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "BugUsers")
@Table(name = "\"BugUsers\"")
@Getter
@Setter
public class BugUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    @Length(min = 4)
    private String password;

    @OneToMany
    private List<ReadingList> readingLists;
}
