package com.example.read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URI;

public class ViewBook extends AppCompatActivity {

    Database myDB;
    Button backBtn, saveBtn;
    BookClass book;
    TextView titleView, descriptionView, authorView, ratingView, pageCountView;
    ImageView previewImage, buyImage, thumbnailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_book);

        myDB = new Database(this);
        backBtn = findViewById(R.id.back);
        saveBtn = findViewById(R.id.saveButton);
        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.descriptionText);
        authorView = findViewById(R.id.authorsText);
        ratingView = findViewById(R.id.ratingText);
        pageCountView = findViewById(R.id.pageCountText);
        thumbnailImage = findViewById(R.id.thumbNailView);





        book = getIntent().getParcelableExtra("bookInfo");

        titleView.setText(book.getTitle());
        descriptionView.setText(book.getDescription());
        authorView.setText(book.getAuthors());
        String rating = Double.toString(book.getRating());
        ratingView.setText(rating);
        String thumbnail = book.getThumbnailLink();

        //thumbnailImage.setImageResource(URI.parse(thumbnail));

        new DownloadImageTask((ImageView) thumbnailImage).execute(thumbnail);

        pageCountView.setText(Integer.toString(book.getPageCount()));

        System.out.println("preview" + book.getPreviewLink());
        System.out.println("buy" + book.getBuyLink());

        if (!book.getPreviewLink().equals("")){
            previewImage = findViewById(R.id.previewImage);
            previewImage.setImageResource(R.drawable.preview);

            previewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(book.getPreviewLink()));
                    startActivity(intent);
                }
            });

        }

        if (!book.getBuyLink().equals("")){
            buyImage = findViewById(R.id.buyLink);
            buyImage.setImageResource(R.drawable.google_store);

            buyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(book.getBuyLink()));
                    startActivity(intent);

                }
            });

        }

    }

    public void save(View v){
        boolean isInserted = myDB.saveFavBooks(book.getTitle(), book.getAuthors(), book.getDescription(), book.getPageCount(), book.getRating(), book.getPreviewLink(), book.getBuyLink(), book.getThumbnailLink());
        Toast.makeText(ViewBook.this, book.getTitle() + "has been saved!", Toast.LENGTH_SHORT).show();

    }

    public void back(View v){
        Intent intent = new Intent(ViewBook.this, SearchBooks.class);
        startActivity(intent);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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



