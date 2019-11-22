package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        searchBtn = findViewById(R.id.search);

    }

    public void searchCall(View v){
        Intent intent = new Intent(UserHome.this, SearchBooks.class);
        intent.putExtra("Uniqid", "From_home");
        startActivity(intent);
    }
}
