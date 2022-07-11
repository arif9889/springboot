package com.example.demo.model;
import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BmiTest {

    @Test
    void bmi_whenHeight171Weight86_equals29_4(){
        double weight = 86, height = 171;
        String username=" ";
        Bmi bmi = new Bmi(weight, height,username);
        Assertions.assertEquals(29.4, bmi.calculateBmi(), 0.05);
    }
}
