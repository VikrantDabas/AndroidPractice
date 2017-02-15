/*
Vikrant Dabas - 800936479
Rohit Katiyar - 800910596
Homework 2
*/
package com.example.vikrant.hw2_group39;

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

public class EditMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Movie mov;
    SeekBar sk;
    Spinner sp;
    EditText name, desc, year, imdb;
    TextView tv;
    static final String KEY = "EDITED";

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
        setContentView(R.layout.activity_edit_movie);
        if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(MainActivity.KEY_E)){
            mov = (Movie)getIntent().getExtras().getSerializable(MainActivity.KEY_E);
            if(mov==null){
                setResult(RESULT_CANCELED);
                finish();
            }
            name = (EditText) findViewById(R.id.editTextNameEdit);
            desc = (EditText) findViewById(R.id.editTextDescEdit);
            year = (EditText) findViewById(R.id.editTextYearEdit);
            imdb = (EditText) findViewById(R.id.editTextIMDBEdit);
            sk = (SeekBar)findViewById(R.id.seekBarEdit);
            sp = (Spinner) findViewById(R.id.spinnerEdit);
            tv = (TextView) findViewById(R.id.textViewSeekBarStatusEdit);
            name.setText(mov.getName());
            desc.setText(mov.getDescription());
            year.setText(mov.getYear());
            imdb.setText(mov.getImdb());
            sk.setProgress(mov.getRating());
            tv.setText(mov.getRating()+"");
        }
        else{
            Toast.makeText(this, "ERROR!!!!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mov.setRating(progress);
                tv.setText(String.valueOf(mov.getRating()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> genereSpinner =ArrayAdapter.createFromResource(EditMovie.this, R.array.add_activity_genres, android.R.layout.simple_spinner_item);
        genereSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(genereSpinner);
        sp.setSelection(genereSpinner.getPosition(mov.getGenre()));
        sp.setOnItemSelectedListener(EditMovie.this);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equals("") || desc.getText().toString().trim().equals("") || year.getText().toString().trim().equals("") || imdb.getText().toString().trim().equals("")){
                    Toast.makeText(EditMovie.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isInt(year.getText().toString().trim())){
                    Toast.makeText(EditMovie.this, "Enter Valid Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    mov.setDescription(desc.getText().toString().trim());
                    mov.setName(name.getText().toString().trim());
                    mov.setYear(year.getText().toString().trim());
                    mov.setImdb(imdb.getText().toString().trim());
                    mov.setRating(sk.getProgress());
                    Intent i = new Intent();
                    i.putExtra(KEY, mov);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mov.setGenre(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
