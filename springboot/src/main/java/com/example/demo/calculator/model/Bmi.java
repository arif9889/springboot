package com.example.demo.calculator.model;

public class Bmi {

    private double weight;
    private double height;

    public Bmi(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public double calculateBmi() {
        return weight / Math.pow(height * 0.01, 2);
    }


}