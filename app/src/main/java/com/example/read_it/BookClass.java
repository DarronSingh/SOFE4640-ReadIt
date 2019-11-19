package com.example.read_it;

import org.json.JSONArray;
import org.json.JSONException;

public class BookClass {
    private String title;
    private JSONArray authors;


    public BookClass() { };

    public BookClass(String title, JSONArray authors){
        this.title = title;
        this.authors=authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() throws JSONException {

        String s = "";

        for (int i = 0; i < authors.length(); i++) {
            s += authors.getString(i) + ", ";
        }

        return s.substring(0, s.length()-2);
    }

    public JSONArray getAuthorsArray() {
        return authors;
    }

    public void setAuthors(JSONArray authors) {
        this.authors = authors;
    }
}