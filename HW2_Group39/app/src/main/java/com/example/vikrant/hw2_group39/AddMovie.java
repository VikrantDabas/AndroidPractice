/*
Vikrant Dabas - 800936479
Rohit Katiyar - 800910596
Homework 2
*/
package com.example.vikrant.hw2_group39;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {
    Movie mov = new Movie();
    static final String KEY = "ADDED";

    private boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);


        final SeekBar sk = (SeekBar) findViewById(R.id.seekBarAdd);
        TextView tV = (TextView) findViewById(R.id.textViewSeekBarStatus);
        tV.setText(String.valueOf(3));
        mov.setRating(3);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tV = (TextView) findViewById(R.id.textViewSeekBarStatus);
                tV.setText(String.valueOf(progress));
                mov.setRating(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> genereSpinner = ArrayAdapter.createFromResource(AddMovie.this, R.array.add_activity_genres, android.R.layout.simple_spinner_item);
        genereSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sp = (Spinner) findViewById(R.id.spinnerAdd);
        sp.setAdapter(genereSpinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mov.setGenre(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                EditText name = (EditText)findViewById(R.id.editTextNameAdd);
                EditText desc = (EditText)findViewById(R.id.editTextDescAdd);
                EditText year = (EditText)findViewById(R.id.editTextYearAdd);
                EditText imdb = (EditText)findViewById(R.id.editTextIMDBAdd);
                if(name.getText().toString().trim().equals("") || desc.getText().toString().trim().equals("") || year.getText().toString().trim().equals("") || imdb.getText().toString().trim().equals("")){
                    Toast.makeText(AddMovie.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isInt(year.getText().toString().trim())){
                    Toast.makeText(AddMovie.this, "Enter Valid Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    mov.setDescription(desc.getText().toString().trim());
                    mov.setName(name.getText().toString().trim());
                    mov.setYear(year.getText().toString().trim());
                    mov.setImdb(imdb.getText().toString().trim());
                    mov.setRating(sk.getProgress());
                    mov.setId(Movie.globalID);
                    Movie.setGlobalID();
                    Intent i = new Intent();
                    i.putExtra(KEY, mov);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }
}
