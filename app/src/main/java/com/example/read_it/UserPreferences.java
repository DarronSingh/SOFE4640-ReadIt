package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    public static final String EXTRA_CHOICEONE = "com.example.application.read_it.EXTRA_CHOICEONE";
    public static final String EXTRA_CHOICETWO = "com.example.application.read_it.EXTRA_CHOICETWO";
    public static final String EXTRA_CHOICETHREE = "com.example.application.read_it.EXTRA_CHOICETHREE";

    Button next;
    Spinner genreOne, genreTwo, genreThree;
    String choiceOne, choiceTwo, choiceThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        next = findViewById(R.id.next);
        genreOne = findViewById(R.id.fantasyOne);
        genreTwo = findViewById(R.id.fantasyTwo);
        genreThree = findViewById(R.id.fantasyThree);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreOne.setAdapter(adapter);
        genreOne.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adapter2 = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreTwo.setAdapter(adapter2);
        genreTwo.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adapter3 = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreThree.setAdapter(adapter3);
        genreThree.setOnItemSelectedListener(this);


    }


    public void next(View v){


        choiceOne = genreOne.getSelectedItem().toString();
        choiceTwo = genreTwo.getSelectedItem().toString();
        choiceThree = genreThree.getSelectedItem().toString();


        Intent intent = new Intent(UserPreferences.this, SearchBooks.class);
        intent.putExtra("Uniqid", "From_pref");
        intent.putExtra(EXTRA_CHOICEONE, choiceOne);
        intent.putExtra(EXTRA_CHOICETWO, choiceTwo);
        intent.putExtra(EXTRA_CHOICETHREE, choiceThree);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            choiceOne = genreOne.getSelectedItem().toString();
            choiceTwo = genreTwo.getSelectedItem().toString();
           choiceThree = genreThree.getSelectedItem().toString();
        Toast.makeText(adapterView.getContext(), "Choices:" + choiceOne + choiceTwo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
