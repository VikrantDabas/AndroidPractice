package com.example.vikrant.thegamesdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vikrant on 2/17/2017.
 */
/*
id>41775</id><GameTitle>Halo: Reach Limited Collector's Edition</GameTitle><ReleaseDate>09/14/2010</ReleaseDate><Platform
 */
public class GameList {
    String id, title, releaseDate, platform;

    public static GameList CreateGame(){
        GameList game=new GameList();
        return game;
    };

    public GameList(){

    }

    public GameList(String id, String title, String releaseDate, String platform) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        if(releaseDate==null || releaseDate.equals("")){
            this.releaseDate = null;
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
        return "GameList{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
