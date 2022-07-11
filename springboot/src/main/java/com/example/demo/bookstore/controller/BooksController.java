package com.example.demo.bookstore.controller;

import com.example.demo.bookstore.model.Book;
import com.example.demo.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BooksController {
    ArrayList<Book> books = new ArrayList<>();

    @Autowired
    BookRepository bookRepository;

    @PostMapping("/books")
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book bookdata) {
//        books.add(bookdata);
        if (bookRepository.existsById(bookdata.getId()))
        bookRepository.save(bookdata);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> retrieveBooks(@RequestParam(value = "authorName", required = false) String authorName) {
        if (authorName == null) {
            List<Book> retrievedBooks = bookRepository.findAll();
            return new ResponseEntity<>(retrievedBooks, HttpStatus.OK);
        }
        List<Book> bookFilter = bookRepository.findByAuthorName(authorName);
        return new ResponseEntity<>(bookFilter, HttpStatus.OK);
    }
//        for (Book element : books) {
//            if (element.getAuthorName().equals(authorName)) {
//                matchingBooks.add(element);
//            }
//        }
//        return new ResponseEntity<>(matchingBooks, HttpStatus.OK);

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> retrieveBooksById(@PathVariable("bookId") int bookId) {
//        for (Book element : books) {
//            if (element.getId() == bookId) {
//                return new ResponseEntity<>(element, HttpStatus.OK);
//            }
//        }
        Book book;
        try {
            book = bookRepository.findById(bookId).get();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> update(@PathVariable("bookId") @Valid int bookId, @RequestBody @Valid Book updatedBook) {
        if (!bookRepository.existsById(bookId)) {
            return ResponseEntity.notFound().build();
        }
        Book retrievedUpdatedBook = bookRepository.save(updatedBook);
        return new ResponseEntity<>(retrievedUpdatedBook, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity deleteById(@PathVariable(value = "bookId", required = false) Integer bookId) {
        if (bookRepository.existsById(bookId)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books")
    public ResponseEntity delete() {
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

