package com.example.demo.calculator.model;

public class User {
    private double weight;
    private double height;
    private String username;

    private double bmi;

    public User(double weight, double height, String username) {
        this.weight = weight;
        this.height = height;
        this.username = username;
    }

    public void setBmi() {
        Bmi bmi = new Bmi(weight, height);
        this.bmi = bmi.calculateBmi();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {

    }
}


