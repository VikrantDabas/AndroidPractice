package com.example.vikrant.thegamesdb;

import java.util.ArrayList;

/**
 * Created by Vikrant on 2/17/2017.
 */

public class Game {
    String baseImageURL, imageURL, trailerURL, genres, publisher, overview, title, releaseDate, platform;
    ArrayList<Integer> id;

    public Game() {
    }

    public Game(String baseImageURL, String imageURL, String trailerURL, String genres, String publisher, String overview, String title, String releaseDate, String platform, ArrayList<Integer> id) {
        this.baseImageURL = baseImageURL;
        this.imageURL = imageURL;
        this.trailerURL = trailerURL;
        this.genres = genres;
        this.publisher = publisher;
        this.overview = overview;
        this.title = title;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.id = id;
    }

    public String getBaseImageURL() {
        return baseImageURL;
    }

    public void setBaseImageURL(String baseImageURL) {
        this.baseImageURL = baseImageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        if(releaseDate==null || releaseDate.trim().equals("")){
            releaseDate=null;
            return;
        }
        String arr[] = releaseDate.split("/");
        this.releaseDate = arr[2];
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Game{" +
                "baseImageURL='" + baseImageURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", trailerURL='" + trailerURL + '\'' +
                ", genres='" + genres + '\'' +
                ", publisher='" + publisher + '\'' +
                ", overview='" + overview + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                ", id=" + id +
                '}';
    }
}
