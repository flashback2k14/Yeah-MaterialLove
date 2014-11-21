package com.yeahdev.materiallovetesting.easyadapterexample;


public class Blindtext {

    private String titleBlindtext;
    private String contentBlindtext;

    public Blindtext(String title, String content) {
        this.titleBlindtext = title;
        this.contentBlindtext = content;
    }

    public String getTitleBlindtext() {
        return this.titleBlindtext;
    }

    public void setTitleBlindtext(String title) {
        this.titleBlindtext = title;
    }

    public String getContentBlindtext() {
        return this.contentBlindtext;
    }

    public void setContentBlindtext(String content) {
        this.contentBlindtext = content;
    }
}