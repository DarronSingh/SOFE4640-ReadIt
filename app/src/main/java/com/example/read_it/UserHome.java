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
    ArrayList<Integer> listViewPage = new ArrayList<>();
    ArrayList<String> listViewImage = new ArrayList<>();

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
               int pageNum = favBooks.getInt(favBooks.getColumnIndex("page_count"));
               String urlImage = favBooks.getString(favBooks.getColumnIndex("thumbnail_link"));

               listViewTitle.add(title);
               listViewDes.add(authors + "\n\n" + description);
               listViewPage.add(pageNum);
               listViewImage.add(urlImage);

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
                int numberOfPages = listViewPage.get(i);
                String imageUrl = listViewImage.get(i);

                Intent intent = new Intent(UserHome.this, ViewSavedBook.class);
                intent.putExtra("BookInfo", t);
                intent.putExtra("BookDes", d);
                intent.putExtra("numOfPages", numberOfPages);
                intent.putExtra("imageUrl", imageUrl);
                startActivity(intent);
            }
        });

    }

    public void searchCall(View v){
        Intent intent = new Intent(UserHome.this, SearchBooks.class);
        intent.putExtra("Uniqid", "From_home");
        startActivity(intent);
    }

    public void viewSavedBooks(){
        Cursor result = myDB.getFavBooks();
        if (result.getCount() == 0) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            buffer.append("Book Title : "+ result.getString(0)+ "\n");
            buffer.append("Book Author : "+ result.getString(1)+ "\n");
            buffer.append("Description: "+ result.getString(2)+ "\n\n");
        }

    }

}
