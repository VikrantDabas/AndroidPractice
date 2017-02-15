/*
Vikrant Dabas
Rohit Katiyar
*/
package com.example.vikrant.wordcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
    HashMap<String, Integer> hm = null;
    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey("result")){
            hm = (HashMap<String, Integer>) getIntent().getSerializableExtra("result");
        }
        else{
            Toast.makeText(this, "Error Ocurred Passing Intents!", Toast.LENGTH_SHORT).show();
            finish();
        }

        LinearLayout scrollView = (LinearLayout)findViewById(R.id.dispRes);

        for(Map.Entry m:hm.entrySet()){
            TextView tV = new TextView(this);
            tV.setText(m.getKey().toString() + ": " + m.getValue().toString());
            scrollView.addView(tV);
        }

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
