package com.example.smartcampus.bean.home;

public class HomeTheme {

    private String theme;
    private boolean isClick;

    public HomeTheme() {

    }

    public HomeTheme(String theme, boolean isClick) {
        this.theme = theme;
        this.isClick = isClick;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
