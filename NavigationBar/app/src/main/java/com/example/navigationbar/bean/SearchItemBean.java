package com.example.navigationbar.bean;

public class SearchItemBean {
    private int id;
    private String text;
    public SearchItemBean(){

    }
    public SearchItemBean(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
