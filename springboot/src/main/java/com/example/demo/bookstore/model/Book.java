package com.example.demo.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id //Penanda primary key
    @Column(name = "Id")
    @Positive
    private int id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "author_name")
    @NotBlank
    private String authorName;

    @Column(name = "year")
    @Positive
    private int year;

    @Column(name = "description")
    @Size(max = 200)
    private String description;

    public Book(int id, String title, String authorName, int year, String description) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.year = year;
        this.description = description;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}