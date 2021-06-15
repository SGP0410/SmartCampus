package com.example.smartcampus.bean;

public class HomeFunction {

    private String name;
    private String color;
    private int image;

    public HomeFunction() {

    }

    public HomeFunction(String name, String color, int image) {
        this.name = name;
        this.color = color;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
