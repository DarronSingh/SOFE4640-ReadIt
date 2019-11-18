package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    final String PREFS = "UserPrefs";

    ImageView logo;
    Intent mainScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun", true);

        if (isFirstRun) {
             mainScreen = new Intent(MainActivity.this, UserPreferences.class);
             getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
        }
        else {
            mainScreen = new Intent(MainActivity.this, mainScreen.class);
        }

        logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(mainScreen);
                    finish();


            }
        }, SPLASH_TIME_OUT);

    }
}
