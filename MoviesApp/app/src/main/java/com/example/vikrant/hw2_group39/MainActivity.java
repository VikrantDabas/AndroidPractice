/*
Vikrant Dabas - 800936479
Rohit Katiyar - 800910596
Homework 2
*/
package com.example.vikrant.hw2_group39;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int REQ_CODE_ADD = 1000;
    static final int REQ_CODE_EDIT = 2004;
    static final String KEY_E = "TO_EDIT";
    static final String KEY_D = "TO_DISPLAY";
    CharSequence[] list;
    ArrayList<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a movie");

        findViewById(R.id.buttonAddMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddMovie.class);
                startActivityForResult(i, REQ_CODE_ADD);
            }
        });

        findViewById(R.id.buttonDeleteMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = new CharSequence[movies.size()];
                if(movies.size() == 0){
                    Toast.makeText(MainActivity.this, "No Movies to Delete!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<movies.size();i++)
                {
                    list[i] = movies.get(i).toString();
                }
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Deleted " + list[which], Toast.LENGTH_SHORT).show();
                        movies.remove(which);
                    }
                }).show();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = new CharSequence[movies.size()];
                if(movies.size() == 0){
                    Toast.makeText(MainActivity.this, "No Movies to Edit!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<movies.size();i++)
                {
                    list[i] = movies.get(i).toString();
                }
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, EditMovie.class);
                        Movie flag = movies.get(which);
                        i.putExtra(MainActivity.KEY_E, flag);
                        //movies.remove(which);
                        startActivityForResult(i, REQ_CODE_EDIT);
                    }
                }).show();
            }
        });

        findViewById(R.id.buttonShowListByYear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.size() == 0){
                    Toast.makeText(MainActivity.this, "No Movies have been Added!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent("com.example.vikrant.hw2_group39.intent.action.VIEWY");
                i.putExtra(KEY_D, movies);
                startActivity(i);
            }
        });

        findViewById(R.id.buttonShowListByRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.size() == 0){
                    Toast.makeText(MainActivity.this, "No Movies have been Added!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent("com.example.vikrant.hw2_group39.intent.action.VIEWR");
                i.putExtra(KEY_D, movies);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQ_CODE_ADD)
        {
            if(resultCode == RESULT_OK && data.getExtras()!=null)
            {
                Movie a;
                a = (Movie)data.getExtras().getSerializable(AddMovie.KEY);
                movies.add(a);
                Toast.makeText(this, a.toString() + " added!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Error Occurred!!", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == MainActivity.REQ_CODE_EDIT)
        {
            if(resultCode == RESULT_OK && data.getExtras()!=null)
            {
                Movie a;
                a = (Movie)data.getExtras().getSerializable(EditMovie.KEY);
                //movies.add(a);
                for(Movie m:movies)
                {
                    if(a.getId() == m.getId()){
                        m.setRating(a.getRating());
                        m.setGenre(a.getGenre());
                        m.setDescription(a.getDescription());
                        m.setImdb(a.getImdb());
                        m.setName(a.getName());
                        m.setYear(a.getYear());
                        break;
                    }
                }
                Toast.makeText(this, a.toString() + " edited!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Error Occurred!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
