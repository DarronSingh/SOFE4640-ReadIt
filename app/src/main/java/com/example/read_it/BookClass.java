package com.example.read_it;

public class BookClass {
    private String title;
    private String authors;


    public BookClass() {
    };

    public BookClass(String title, String authors){
        this.title = title;
        this.authors=authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}