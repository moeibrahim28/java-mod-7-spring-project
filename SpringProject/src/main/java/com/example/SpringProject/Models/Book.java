package com.example.SpringProject.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Books")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String title;

    @Min(1)
    private int pages;

    @CreatedDate
    @Column(name = "published_at")
    private LocalDateTime published;

    @ManyToMany(mappedBy = "bookSet", cascade = CascadeType.DETACH)
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;
}
