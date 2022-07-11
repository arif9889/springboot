package com.example.demo.calculator.controller;

import com.example.demo.calculator.model.Bmi;
import com.example.demo.calculator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BmiController {
    ArrayList<User> user = new ArrayList<>();

    @GetMapping("/calculateBmi")
    public String calculateBmi(@RequestParam("weight") double weight,
                               @RequestParam("height") double height,
                               @RequestParam("username") String username) {
        User user = new User(weight, height, username);
        Bmi bmi = new Bmi(weight,height);
        return "BMI : " + bmi.calculateBmi();
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User userdata) {
        userdata.setBmi();
        user.add(userdata);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("bmi/users/all")
    public ResponseEntity<List<User>> retrieveUserBmiList() {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
