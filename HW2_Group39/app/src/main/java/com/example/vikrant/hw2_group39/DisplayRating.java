/*
Vikrant Dabas - 800936479
Rohit Katiyar - 800910596
Homework 2
*/
package com.example.vikrant.hw2_group39;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DisplayRating extends AppCompatActivity {
    ArrayList<Movie> movies;
    int position;
    SpannableStringBuilder sb;
    TextView title, genre, rating, year, imdb, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rating);
        final StyleSpan bss = new StyleSpan(Typeface.BOLD);
        sb = new SpannableStringBuilder();
        title = (TextView)findViewById(R.id.textViewNameRating);
        genre = (TextView)findViewById(R.id.textViewGenereRating);
        rating = (TextView)findViewById(R.id.textViewRatingRating);
        year = (TextView)findViewById(R.id.textViewYearRating);
        imdb = (TextView)findViewById(R.id.textViewImdbRating);
        desc = (TextView)findViewById(R.id.textViewDescRating);
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey(MainActivity.KEY_D)){
            movies = (ArrayList<Movie>)getIntent().getSerializableExtra(MainActivity.KEY_D);
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if(o1.getRating() > o2.getRating()){
                        return -1;
                    }
                    else if(o1.getRating() < o2.getRating()){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            });
        }
        else{
            finish();
        }
        position = 0;
        sb.append("Title: ");
        sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(movies.get(position).getName());
        title.setText(sb);
        desc.setText(movies.get(position).getDescription());
        sb.clear();
        sb.append("Genre: ");
        sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(movies.get(position).getGenre());
        genre.setText(sb);
        sb.clear();
        sb.append("Rating: ");
        sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(String.valueOf(movies.get(position).getRating()));
        sb.append(" / 5");
        rating.setText(sb);
        sb.clear();
        sb.append("Year: ");
        sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(movies.get(position).getYear());
        year.setText(sb);
        imdb.setText(movies.get(position).getImdb());

        findViewById(R.id.firstRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                sb.clear();
                sb.append("Title: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getName());
                title.setText(sb);
                desc.setText(movies.get(position).getDescription());
                sb.clear();
                sb.append("Genre: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getGenre());
                genre.setText(sb);
                sb.clear();
                sb.append("Rating: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(String.valueOf(movies.get(position).getRating()));
                sb.append(" / 5");
                rating.setText(sb);
                sb.clear();
                sb.append("Year: ");
                sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getYear());
                year.setText(sb);
                imdb.setText(movies.get(position).getImdb());
            }
        });
        findViewById(R.id.lastRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = movies.size()-1;
                sb.clear();
                sb.append("Title: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getName());
                title.setText(sb);
                desc.setText(movies.get(position).getDescription());
                sb.clear();
                sb.append("Genre: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getGenre());
                genre.setText(sb);
                sb.clear();
                sb.append("Rating: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(String.valueOf(movies.get(position).getRating()));
                sb.append(" / 5");
                rating.setText(sb);
                sb.clear();
                sb.append("Year: ");
                sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getYear());
                year.setText(sb);
                imdb.setText(movies.get(position).getImdb());
            }
        });
        findViewById(R.id.nextRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == movies.size()-1){
                    Toast.makeText(DisplayRating.this, "Already at last position.", Toast.LENGTH_SHORT).show();
                    return;
                }
                position++;
                sb.clear();
                sb.append("Title: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getName());
                title.setText(sb);
                desc.setText(movies.get(position).getDescription());
                sb.clear();
                sb.append("Genre: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getGenre());
                genre.setText(sb);
                sb.clear();
                sb.append("Rating: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(String.valueOf(movies.get(position).getRating()));
                sb.append(" / 5");
                rating.setText(sb);
                sb.clear();
                sb.append("Year: ");
                sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getYear());
                year.setText(sb);
                imdb.setText(movies.get(position).getImdb());
            }
        });
        findViewById(R.id.previousRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Toast.makeText(DisplayRating.this, "Already at first position.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sb.clear();
                position--;
                sb.append("Title: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getName());
                title.setText(sb);
                desc.setText(movies.get(position).getDescription());
                sb.clear();
                sb.append("Genre: ");
                sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getGenre());
                genre.setText(sb);
                sb.clear();
                sb.append("Rating: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(String.valueOf(movies.get(position).getRating()));
                sb.append(" / 5");
                rating.setText(sb);
                sb.clear();
                sb.append("Year: ");
                sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(movies.get(position).getYear());
                year.setText(sb);
                imdb.setText(movies.get(position).getImdb());
            }
        });
        findViewById(R.id.buttonFinishRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}