package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class ViewBook extends AppCompatActivity {

    Database myDB;
    Button backBtn, saveBtn;
    BookClass book;
    TextView titleView, descriptionView, authorView, ratingView, pageCountView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_book);

        myDB = new Database(this);
        backBtn = findViewById(R.id.back);
        saveBtn = findViewById(R.id.saveButton);
        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.descriptionText);
        authorView = findViewById(R.id.authorsText);
        ratingView = findViewById(R.id.ratingText);
        pageCountView = findViewById(R.id.pageCountText);



        book = getIntent().getParcelableExtra("bookInfo");

        titleView.setText(book.getTitle());
        descriptionView.setText(book.getDescription());
        authorView.setText(book.getAuthors());
        String rating = Double.toString(book.getRating());
        ratingView.setText(rating);

        pageCountView.setText(Integer.toString(book.getPageCount()));

    }

    public void save(View v){
        boolean isInserted = myDB.saveFavBooks(book.getTitle(), book.getAuthors(), book.getDescription(), book.getPageCount(), book.getRating());
        Toast.makeText(ViewBook.this, book.getTitle() + "has been saved!", Toast.LENGTH_SHORT).show();

    }

    public void back(View v){
        Intent intent = new Intent(ViewBook.this, SearchBooks.class);
        startActivity(intent);
    }
}
