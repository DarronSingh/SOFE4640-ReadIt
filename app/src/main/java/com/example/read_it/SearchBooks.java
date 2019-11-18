package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchBooks extends AppCompatActivity {

    TextView search;
    Button searchBtn;
    ListView viewSearch;
    ArrayList<BookClass> books;
    final String key = "AIzaSyDIgCNZ9J2qBFeCT8gxVW4-ZRZd4bllw-0";
    String URI = "https://www.googleapis.com/books/v1/volumes?q=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        search = findViewById(R.id.searchBar);
        searchBtn = findViewById(R.id.search);
        viewSearch = findViewById(R.id.viewBooks);


    }

    public void searchBooks(View v){
        RequestQueue queue = Volley.newRequestQueue(this);

        String searchQuery = search.getText().toString();
        String query = searchQuery.replace(" ", "+");
        String finalQuery = URI+query;




        StringRequest stringRequest = new StringRequest(Request.Method.GET, URI+query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(), response + " C", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error with request!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }

    public void JsonParser(String finalQuery){


    }

}
