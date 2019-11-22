package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewBook extends AppCompatActivity {

    Button backBtn, saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        backBtn = findViewById(R.id.back);
        saveBtn = findViewById(R.id.saveButton);
    }

    public void back(View v){
        Intent intent = new Intent(ViewBook.this, SearchBooks.class);
        startActivity(intent);
    }
}
