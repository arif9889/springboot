package com.example.demo.bookstore.repository;

import com.example.demo.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(value = "SELECT b FROM Book b WHERE b.authorName = :authorName")
    List<Book> findByAuthorName(@Param("authorName")String authorName);
}
