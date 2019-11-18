package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainScreen extends AppCompatActivity {

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        search = findViewById(R.id.searchBooks);
    }

    public void searchBooks(View v){
        Intent intent = new Intent(mainScreen.this, SearchBooks.class);
        startActivity(intent);
    }
}
