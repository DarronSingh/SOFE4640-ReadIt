package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserPreferences extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        next = findViewById(R.id.next);
    }


    public void next(View v){
        Intent intent = new Intent(UserPreferences.this, SearchBooks.class);
        startActivity(intent);
    }
}
