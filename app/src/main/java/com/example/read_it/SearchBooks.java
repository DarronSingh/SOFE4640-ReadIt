package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBooks extends AppCompatActivity {

    TextView search;
    Button searchBtn;
    ListView viewSearch;
    ArrayList<BookClass> books = new ArrayList<BookClass>();
    final String key = "AIzaSyDIgCNZ9J2qBFeCT8gxVW4-ZRZd4bllw-0";
    String URI = "https://www.googleapis.com/books/v1/volumes?q=";

    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
    String[] from = {"listview_image", "listview_title", "listview_discription"};
    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

    // Array of strings for ListView Title
    ArrayList<String> listviewTitle = new ArrayList<>();

    ArrayList<String> listviewShortDescription = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        search = findViewById(R.id.searchBar);
        searchBtn = findViewById(R.id.search);
        viewSearch = findViewById(R.id.viewBooks);

        /*
        If statement below checks which activity is calling the search books activity
         */
        Intent intent = this.getIntent();
        if(intent != null){
            String strData = intent.getExtras().getString("Uniqid");
            if(strData.equals("From_pref")){
                String firstPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICEONE)));
                String secondPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICETWO)));
                String thirdPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICETHREE)));
                System.out.println("First PRef" + firstPref);
                searchBooks(firstPref);

            }
            else if (strData.equals("From_home")){
                //search.setText("");
            }
        }

        try {
            setupAdapter(books.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setupAdapter(int length) throws JSONException {

        for(BookClass book: books) {
            listviewShortDescription.add(book.getAuthors());
            listviewTitle.add(book.getTitle());
        }

        for (int i = 0; i < length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle.get(i));
            hm.put("listview_discription", listviewShortDescription.get(i));
            System.out.println(listviewShortDescription.get(i));
            hm.put("listview_image", Integer.toString(R.drawable.logo));
            aList.add(hm);
        }

        System.out.println("73");

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        viewSearch.setAdapter(simpleAdapter);

        viewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchBooks.this, ViewBook.class);
                startActivity(intent);
            }
        });

    }

    public void searchBooks(View v){
        RequestQueue queue = Volley.newRequestQueue(this);

        String searchQuery = search.getText().toString();
        String query = searchQuery.replace(" ", "+");
        String finalQuery = URI+query;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalQuery,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        Toast.makeText(getApplicationContext(), response + " C", Toast.LENGTH_LONG).show();
                        try {
                            JsonParser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error with request!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

    }

    public void searchBooks(String genre){
        RequestQueue queue = Volley.newRequestQueue(this);

        String finalQuery = URI+"subject:"+genre;
        System.out.println(finalQuery);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalQuery,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        Toast.makeText(getApplicationContext(), response + " C", Toast.LENGTH_LONG).show();
                        try {
                            JsonParser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error with request!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    public void JsonParser(String response) throws JSONException {
        // Convert String to json object
        JSONObject json = new JSONObject(response);

        // Get items object (contains all books)
        JSONArray items = json.getJSONArray("items");

        int responseLength = items.length() > 40 ? 40 : items.length();

        for (int i = 0; i < responseLength; i++) {
            JSONObject bookJson = items.getJSONObject(i).getJSONObject("volumeInfo");
            BookClass book = new BookClass(bookJson.getString("title"), bookJson.getJSONArray("authors"));
            books.add(book);
        }

        setupAdapter(responseLength);
    }

}
