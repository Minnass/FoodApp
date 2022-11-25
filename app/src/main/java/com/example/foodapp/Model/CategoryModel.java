package com.example.foodapp.Model;

public class CategoryModel {
    private  String title;
    private  int id;

    public CategoryModel(String title, int id) {
        this.title = title;
            this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
