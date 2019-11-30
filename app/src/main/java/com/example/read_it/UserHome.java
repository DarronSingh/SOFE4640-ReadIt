package com.example.read_it;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserHome extends AppCompatActivity {
    Button searchBtn;
    ListView savedBooks;
    Cursor favBooks;
    int counter = 0;

    Database myDB;

    ArrayList<String> listViewTitle = new ArrayList<>();
    ArrayList<String> listViewDes = new ArrayList<>();

    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
    String[] from = {"listview_image", "listview_title", "listview_discription"};
    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        myDB = new Database(this);
        viewSavedBooks();

        searchBtn = findViewById(R.id.search);
        savedBooks = findViewById(R.id.savedBooks);

       favBooks = myDB.getFavBooks();

       if (favBooks.moveToFirst()){
           while (!favBooks.isAfterLast()){
               counter++;
               String title = favBooks.getString(favBooks.getColumnIndex("title"));
               String authors = favBooks.getString(favBooks.getColumnIndex("author_name"));
               String description = favBooks.getString(favBooks.getColumnIndex("description"));

               listViewTitle.add(title);
               listViewDes.add(authors + "\n\n" + description);

               favBooks.moveToNext();
           }
           favBooks.close();
       }

       for (int i = 0; i<counter; i++){
           HashMap<String, String> hm = new HashMap<>();
           hm.put("listview_title", listViewTitle.get(i));
           hm.put("listview_discription", listViewDes.get(i));
           hm.put("listview_image", Integer.toString(R.drawable.logo));
           aList.add(hm);
       }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        savedBooks.setAdapter(simpleAdapter);

        savedBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String t = listViewTitle.get(i);
                String d = listViewDes.get(i);
                Intent intent = new Intent(UserHome.this, ViewSavedBook.class);
                intent.putExtra("BookInfo", t);
                intent.putExtra("BookDes", d);
                startActivity(intent);
            }
        });

//(ArrayAdapter) savedBooks.getAdapter().getItem(i);
    }

    public void searchCall(View v){
        Intent intent = new Intent(UserHome.this, SearchBooks.class);
        intent.putExtra("Uniqid", "From_home");
        startActivity(intent);
    }

    public void viewSavedBooks(){
        Cursor result = myDB.getFavBooks();
        if (result.getCount() == 0) {
            //Show message
            showMessage("Error", "No Data Found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            buffer.append("Book Title : "+ result.getString(0)+ "\n");
            buffer.append("Book Author : "+ result.getString(1)+ "\n");
            buffer.append("Description: "+ result.getString(2)+ "\n\n");
        }
        //show all data
        showMessage("Data", buffer.toString());

    }

    private void showMessage(String error, String no_data_found) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(error);
        builder.setMessage(no_data_found);
        builder.show();

    }
}
