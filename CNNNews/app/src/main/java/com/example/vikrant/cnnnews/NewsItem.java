package com.example.vikrant.cnnnews;

import java.io.Serializable;

/**
 * Created by Vikrant on 2/13/2017.
 */

public class NewsItem implements Serializable {
    //title, description, pubDate, and a square image url
    String title, description, pubDate, imageURL;

    public NewsItem() {
        imageURL="";
    }

    public NewsItem(String title, String description, String pubDate, String imageURL) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
