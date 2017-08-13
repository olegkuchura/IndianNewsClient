package com.olegkuchura.android.indiannewsclient.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleg on 09.08.2017.
 */

public class PieceOfNews {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("description")
    private String description;

    @SerializedName("publishedAt")
    private Date date;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    public PieceOfNews(String title, String author, String description, Date date, String url, String urlToImage) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.date = date;
        this.url = url;
        this.urlToImage = urlToImage;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getDateAsString(){
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd MMMM yyyy");
        return dateFormat.format( date );
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PieceOfNews that = (PieceOfNews) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return urlToImage != null ? urlToImage.equals(that.urlToImage) : that.urlToImage == null;

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
