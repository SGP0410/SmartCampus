package com.example.smartcampus.bean.yinYong;

public class ApplyType {
    
    private String name;
    private int image;
    private int color;
    
    public ApplyType(String name, int image, int color) {
        this.name = name;
        this.image = image;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getImage() {
        return image;
    }
    
    public void setImage(int image) {
        this.image = image;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
}
