package com.example.read_it;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

public class BookClass implements Parcelable {
    private String title;
    private String authors;
    private String description;
    private int pageCount;
    private double rating;
    private String imageUrl;
    private String previewLink;
    private String buyLink;
    private String thumbnailLink;


    public BookClass() { };

    public BookClass(String title, String authors, String description, int pageCount, double rating, String previewLink, String buyLink, String thumbnailLink){
        this.title = title;
        this.authors=authors;
        this.description = description;
        this.pageCount = pageCount;
        this.rating = rating;
        this.previewLink = previewLink;
        this.buyLink = buyLink;
        this.thumbnailLink = thumbnailLink;
        //this.imageUrl = imageUrl;
    }

    protected BookClass(Parcel in) {
        title = in.readString();
        authors = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        rating = in.readDouble();
        previewLink = in.readString();
        buyLink = in.readString();
        thumbnailLink = in.readString();
        //imageUrl = in.readString();
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public static final Creator<BookClass> CREATOR = new Creator<BookClass>() {
        @Override
        public BookClass createFromParcel(Parcel in) {
            return new BookClass(in);
        }

        @Override
        public BookClass[] newArray(int size) {
            return new BookClass[size];
        }
    };

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors(){

        return this.authors;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(authors);
        parcel.writeString(description);
        parcel.writeInt(pageCount);
        parcel.writeDouble(rating);
        parcel.writeString(previewLink);
        parcel.writeString(buyLink);
        parcel.writeString(thumbnailLink);
        //parcel.writeString(imageUrl);



    }
}