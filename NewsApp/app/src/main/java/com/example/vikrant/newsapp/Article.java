/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.newsapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vikrant on 2/6/2017.
 */

public class Article {
    String urlToImage, title, author, description, publishedAt;

    static public Article createArticle(JSONObject articlesJSONObject) throws JSONException {
        Article per = new Article();
        per.setUrlToImage(articlesJSONObject.getString("urlToImage"));
        per.setTitle(articlesJSONObject.getString("title"));
        per.setAuthor(articlesJSONObject.getString("author"));
        per.setDescription(articlesJSONObject.getString("description"));
        per.setPublishedAt(articlesJSONObject.getString("publishedAt"));
        return per;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "urlToImage='" + urlToImage + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
