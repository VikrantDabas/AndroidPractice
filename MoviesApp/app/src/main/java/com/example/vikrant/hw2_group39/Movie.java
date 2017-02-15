/*
Vikrant Dabas - 800936479
Rohit Katiyar - 800910596
Homework 2
*/
package com.example.vikrant.hw2_group39;

import java.io.Serializable;

/**
 * Created by Vikrant on 1/28/2017.
 */

public class Movie implements Serializable{
    static int globalID;

    static {
        globalID=0;
    }

    static void setGlobalID(){
        globalID++;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String genre;
    private String description;
    private int rating;
    private String year;
    private String imdb;

    public Movie(String name, String genre, String description, int rating, String year, String imdb) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.year = year;
        this.imdb = imdb;
    }

    public Movie(){

    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    @Override
    public String toString() {
        //return "Movie{" + "name='" + name + '\'' + '}';
        return name;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }
}
