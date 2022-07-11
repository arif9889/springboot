package com.example.demo.controller;

import com.example.demo.calculator.controller.BmiController;

import com.example.demo.model.Bmi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BmiController.class)
public class BMIControllerTest {
    @Autowired
    private static ObjectMapper mapper;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
    }

    @Autowired
    MockMvc mvc;

    @Test
    void BMI_whenHeight171_Weight86_equals29_4() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/bmi?weight=86&height=171&username=Andrew"))
                //handle the result from server
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                //maching the status is ok
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("29.4")));

    }

    @Test
    void save_shouldSaveUserToArrayList() throws Exception {
        Bmi user = new Bmi(86, 171, "Andrew");
        this.mvc
                .perform(MockMvcRequestBuilders.post("/bmi")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(user)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
