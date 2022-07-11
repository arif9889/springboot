package com.example.demo.bookstore.controller;

import com.example.demo.bookstore.model.Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BooksController.class)
class BooksControllerTest {

    private static ObjectMapper mapper;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
    }

    @Autowired
    MockMvc mvc;

    @Test
    void saveBook_shouldSaveBookToTheArray() throws Exception {
        Book book = new Book(1, "Anything", "Anyone", 1992, "Nothing here");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isAccepted());
    }

    @Test
    void saveBook_shouldNotSaveUser_whenIdIsNegative() throws Exception {
        Book book = new Book(-1, "Anything", "Anyone", 1992, "Nothing here");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveBook_shouldNotSaveUser_whenTitleIsNull() throws Exception {
        Book book = new Book(-1, "", "Anyone", 1992, "Nothing here");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveBook_givenDescriptionMoreThan200Character_shouldNotSaveUserToTheArray() throws Exception {
        Book book = new Book(
                -1,
                "",
                "Anyone",
                1992, "I want a giraffe, but I'm a turtle eating waffles.\n" +
                "She lived on Monkey Jungle Road and that seemed to explain all of her strangeness.\n" +
                "The old apple revels in its authority.\n" +
                "It was at that moment that he learned there are certain parts of the body that you should never Nair.");

        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveBook_givenYearIsNegative_shouldNotSaveUserToTheArray() throws Exception {
        Book book = new Book(1, "Natural Style", "Anyone", -1992, "Nothing here");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveBook_shouldNotSaveBookTheArrayList_titleAndAuthorNull() throws Exception {
        //Arrange
        Book book = new Book(1, null, null, 2012, "Nothing Here");
        BooksController bookController = new BooksController();
        //Action
        ResponseEntity<Book> bookResponseEntity = bookController.saveBook(book);
        Assertions.assertTrue(bookResponseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    void saveBook_shouldNotSaveBookTheArrayList_titleNull() throws Exception {
        //Arrange
        Book book = new Book(1, null, "David", 2012, "Nothing Here");
        BooksController bookController = new BooksController();
        //Action
        ResponseEntity<Book> bookResponseEntity = bookController.saveBook(book);
        Assertions.assertTrue(bookResponseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    void saveBook_shouldNotSaveBookTheArrayList_authorNull() throws Exception {
        //Arrange
        Book book = new Book(1, "Bowl", null, 2012, "Nothing Here");
        BooksController bookController = new BooksController();
        //Action
        ResponseEntity<Book> bookResponseEntity = bookController.saveBook(book);
        Assertions.assertTrue(bookResponseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    void retrieveBookList() throws Exception {
        Book book = new Book(1, "Java", "Arif", 2012, "Nothing Here");
        Book book2 = new Book(2, "Python", "Fira", 2010, "Python guide book");
        this.mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(book)));

        this.mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(book2)));

        this.mvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(3)));
    }

    @Test
    void book_givenAuthorName_shouldShowBook() throws Exception {
        //Arrange
        Book book = new Book(3, "Java", "Heco", 2020, "awwww");
        Book book2 = new Book(4, "Python", "San", 2020, "awwww");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)));

        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book2)));

        this.mvc
                .perform(MockMvcRequestBuilders.get("/books?authorName=Heco"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(3)));
    }

    @Test
    void retrieveBook_givenId_shouldShowBook() throws Exception {
        //Arrange
        Book book = new Book(3, "Java", "Heco", 2020, "awwww");
        Book book2 = new Book(4, "Python", "San", 2020, "awwww");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)));
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book2)));
        this.mvc
                .perform(MockMvcRequestBuilders.get("/books/3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(3)));
    }

    @Test
    void updateYear_givenId_shouldUpdateBook() throws Exception {
        Book book = new Book(3, "Java", "Heco", 2020, "awwww");
        Book updatedBook = new Book(3, "Python", "Heco", 2021, "Hellaw");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(book)));

        this.mvc
                .perform(MockMvcRequestBuilders.put("/books/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(updatedBook)))
                .andExpect(status().isOk());

        this.mvc
                .perform(MockMvcRequestBuilders.get("/books/3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("year", CoreMatchers.is(2021)))
                .andExpect(MockMvcResultMatchers.jsonPath("description", CoreMatchers.is("Hellaw")));
    }

    @Test
    void delete_givenId_shouldRemoveBookAndShouldReturnStatusCode204() throws Exception {
        // Arrange
        Book book = new Book(1, "Rice", "Abc", 2011, "Just a rice");
        this.mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(book)));

        // Action
        this.mvc.perform(MockMvcRequestBuilders.delete("/books/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        // Assert
        this.mvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(0)));
    }


    @Test
    void deleteAllBook() throws Exception {
        // Arrange
        Book book = new Book(1, "Rice", "Abc", 2011, "Just a rice");
        Book book2 = new Book(2, "Aice", "cba", 2021, "Just a Aice");
        this.mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(book)));

        this.mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(book2)));

        // Action
        this.mvc.perform(MockMvcRequestBuilders.delete("/books"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        // Assert
        this.mvc
                .perform(MockMvcRequestBuilders.get("/books"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(0)));
    }

}
