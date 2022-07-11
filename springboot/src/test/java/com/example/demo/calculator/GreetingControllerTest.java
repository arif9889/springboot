package com.example.demo.calculator;

import com.example.demo.bookstore.controller.BooksController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BooksController.class)
class GreetingControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void getFooByNames() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/greetings/andrew"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Hello: andrew")));
    }

    @Test
    void getFooByName() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/greetings?Nama=Andrew"))
                //handle the result from server
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                //maching the status is ok
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Hello: Andrew")));

    }

}