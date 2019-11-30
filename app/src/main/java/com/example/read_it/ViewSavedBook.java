package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewSavedBook extends AppCompatActivity {

    Database myDB;
    TextView textT;
    TextView textD;
    Button deleteBtn, updateBtn;
    EditText pagesFinished;
    ProgressBar mProgress;
    int UserNumOfPages, TotalPageNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_book);

        myDB = new Database(this);
        textT = findViewById(R.id.viewBookTextView);
        textD = findViewById(R.id.desTextView);
        deleteBtn = findViewById(R.id.deleteBtn);
        pagesFinished = findViewById(R.id.pagesFinished);
        updateBtn = findViewById(R.id.updateBtn);
        mProgress = findViewById(R.id.progressBar);
        mProgress.setProgress(0);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b!= null) {
            String t = (String) b.get("BookInfo");
            String d = (String) b.get("BookDes");
            TotalPageNum = (int) b.get("numOfPages");
            textT.setText(t);
            textD.setText(d);
        }
        delete();


        }



    public void updateProgressBar(View v){
        String pages = pagesFinished.getText().toString();

        if (!pages.equals("")){
            UserNumOfPages = Integer.parseInt(pages);
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter Number", Toast.LENGTH_SHORT).show();
        }

       // Double percentComplete = ((UserNumOfPages/TotalPageNum) * 100.0);


        int percentComplete = (int) Math.floor((UserNumOfPages*100.0)/TotalPageNum);
        System.out.print( "Pages complete" + percentComplete);


        mProgress.setProgress(percentComplete);
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
                back();
            }
        });

    }

    public void back(){
        Intent intent = new Intent(ViewSavedBook.this, UserHome.class);
        startActivity(intent);
    }

    public void back(View v){
        Intent intent = new Intent(ViewSavedBook.this, UserHome.class);
        startActivity(intent);
    }
}
