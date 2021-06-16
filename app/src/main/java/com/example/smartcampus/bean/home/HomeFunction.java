package com.example.smartcampus.bean.home;

public class HomeFunction {

    private String name;
    private int color;
    private int image;

    public HomeFunction() {

    }

    public HomeFunction(String name, int color, int image) {
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
