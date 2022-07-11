package com.example.demo.model;

// Bmi->User
public class Bmi {
    private double weight;
    private double height;
    private String username;
    private double bmi;
    public Bmi(double weight, double height, String username) {
        this.weight = weight;
        this.height = height;
        this.username = username;
    }
    public void setBmi(){

        this.bmi = calculateBmi();
    }
    public double getBmi(){

        return this.bmi;
    }
//constructor
public Bmi(){

}
    public double calculateBmi()
    {
        return weight/Math.pow(height * 0.01,2);
    }

    public double getWeight(){
        return weight;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
    public double getHeight(){
        return height;
    }
    public void setHeight(double height){
        this.height = height;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

}
