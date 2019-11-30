package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewSavedBook extends AppCompatActivity {

    Database myDB;
    TextView textT;
    TextView textD;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_book);

        myDB = new Database(this);
        textT = findViewById(R.id.viewBookTextView);
        textD = findViewById(R.id.desTextView);
        deleteBtn = findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b!= null) {
            String t = (String) b.get("BookInfo");
            String d = (String) b.get("BookDes");
            textT.setText(t);
            textD.setText(d);
        }
        delete();
    }

    public void delete() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle b = intent.getExtras();

                if (b!= null) {
                    String t = (String) b.get("BookInfo");
                    Integer delete = myDB.delete(t);
                }
            }
        });
    }

    public void back(View v){
        Intent intent = new Intent(ViewSavedBook.this, UserHome.class);
        startActivity(intent);
    }
}
