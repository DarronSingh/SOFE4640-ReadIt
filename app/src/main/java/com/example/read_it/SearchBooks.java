package com.example.read_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        If statement below checks which activity is calling the search books activity
         */
        Intent intent = this.getIntent();
        if (intent != null) {
            String strData = intent.getExtras().getString("Uniqid");
            if (strData.equals("From_pref")) {
                String firstPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICEONE)));
                String secondPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICETWO)));
                String thirdPref = (intent.getStringExtra((UserPreferences.EXTRA_CHOICETHREE)));
                System.out.println("First PRef" + firstPref);
                searchBooks(firstPref);

            } else if (strData.equals("From_home")) {
            }
        }

        try {
            setupAdapter(books.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    /*
    Sets up list view for search results
     */
    public void setupAdapter(int length) throws JSONException {


        viewSearch.setAdapter(null);
        for (BookClass book : books) {
            listviewShortDescription.add(book.getAuthors());
            listviewTitle.add(book.getTitle());
            //listviewImage.add(book.getImageUrl());

        }

        for (int i = 0; i < length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle.get(i));
            hm.put("listview_discription", listviewShortDescription.get(i));
            System.out.println(listviewShortDescription.get(i));
            //hm.put("listview_image", listviewImage.get(i));
            hm.put("listview_image", Integer.toString(R.drawable.logo));


            aList.add(hm);
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        viewSearch.setAdapter(simpleAdapter);

        /*
        opens intent with book info if user clicks on specific book
         */

        viewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchBooks.this, ViewBook.class);
                intent.putExtra("bookInfo", books.get(i));
                startActivity(intent);
            }
        });

    }

    public void searchBooks(View v) {
        books.clear();
        viewSearch.setAdapter(null);
        RequestQueue queue = Volley.newRequestQueue(this);

        String searchQuery = search.getText().toString();
        String query = searchQuery.replace(" ", "+");
        String finalQuery = URI + query;

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

    public void searchBooks(String genre) {
        books.clear();
        viewSearch.setAdapter(null);
        RequestQueue queue = Volley.newRequestQueue(this);

        String finalQuery = URI + "subject:" + genre;
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
            JSONObject saleJson = items.getJSONObject(i).getJSONObject("saleInfo");

            String title = bookJson.has("title") ? bookJson.getString("title") : "";
            JSONArray authors = bookJson.has("authors") ? bookJson.getJSONArray("authors") : new JSONArray();
            String authorsString = this.getAuthorsString(authors);
            System.out.println("Author:" + authors);
            String description = bookJson.has("description") ? bookJson.getString("description") : "";
            int pageCount = bookJson.has("pageCount") ? bookJson.getInt("pageCount") : 0;
            double rating = bookJson.has("averageRating") ? bookJson.getDouble("averageRating") : 0.0;
            String imageUrl = bookJson.has("imageLinks") ? bookJson.getJSONObject("imageLinks").getString("thumbnail") : "";
            System.out.println("URL: " + imageUrl);
            String previewLink = bookJson.has("previewLink") ? bookJson.getString("previewLink") : "";
            String buyLink = saleJson.has("buyLink") ? saleJson.getString("buyLink") : "";

            BookClass book = new BookClass(title,authorsString, description, pageCount, rating, previewLink, buyLink, imageUrl, 0);
            books.add(book);
        }

        setupAdapter(responseLength);
    }

    public String getAuthorsString(JSONArray authors) throws JSONException {

        String s = "";

        for (int i = 0; i < authors.length(); i++) {
            s += authors.getString(i) + ", ";
        }
        System.out.println("string" + s);
        return s.length() < 2 ? s : s.substring(0, s.length()-2);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
