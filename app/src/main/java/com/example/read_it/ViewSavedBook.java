package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

public class ViewSavedBook extends AppCompatActivity {

    Database myDB;
    TextView textT;
    TextView textD, totalPagesText;
    Button deleteBtn, updateBtn;
    EditText pagesFinished;
    ProgressBar mProgress;
    ImageView thumnail;

    int UserNumOfPages, TotalPageNum, progress;
    String imageURL, title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_book);

        getSupportActionBar().setTitle("Saved Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new Database(this);
        textT = findViewById(R.id.viewBookTextView);
        textD = findViewById(R.id.desTextView);
        totalPagesText = findViewById(R.id.totalPagesText);
        textD.setMovementMethod(new ScrollingMovementMethod());
        deleteBtn = findViewById(R.id.deleteBtn);
        pagesFinished = findViewById(R.id.pagesFinished);
        thumnail = findViewById(R.id.thumbnailView);
        updateBtn = findViewById(R.id.updateBtn);
        mProgress = findViewById(R.id.progressBar);
        mProgress.setProgress(0);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle!= null) {
            title = (String) bundle.get("BookInfo");
            description = (String) bundle.get("BookDes");
            TotalPageNum = (int) bundle.get("numOfPages");
            imageURL = (String) bundle.get("imageUrl");
            progress = (int) bundle.get("progress");
            new DownloadImageTask(thumnail).execute(imageURL);
            Cursor c = myDB.getSavedBookProgress(this.title);
            c.moveToFirst();
            progress = c.getInt(c.getColumnIndex("progress"));
            mProgress.setProgress(progress);
            textT.setText(title);
            textD.setText(description);
        }

        totalPagesText.setText("Out of " + TotalPageNum);
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
        int percentComplete = (int) Math.floor((UserNumOfPages*100.0)/TotalPageNum);
        mProgress.setProgress(percentComplete);
        myDB.updateProgress(title, percentComplete);
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

    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
