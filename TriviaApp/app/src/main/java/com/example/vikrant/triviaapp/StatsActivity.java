/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    private ArrayList<Question> questions;
    private int correct=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey("QUES")){
            questions = (ArrayList<Question>) getIntent().getExtras().getSerializable("QUES");
        } else {
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            finish();
        }

        LinearLayout scrollViewLinearLayout = (LinearLayout)findViewById(R.id.linearLayoutStats);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<questions.size();i++){
            TextView ques = new TextView(StatsActivity.this);
            TextView userAns = new TextView(StatsActivity.this);
            TextView ans = new TextView(StatsActivity.this);
            ques.setText(questions.get(i).getText());
            if(questions.get(i).getUserChoice()!=-1){
                userAns.setText(questions.get(i).getChoices().getChoice().get(questions.get(i).getUserChoice()));
            } else {
                userAns.setText("");
            }
            ans.setText(questions.get(i).getChoices().getChoice().get(Integer.valueOf(questions.get(i).getChoices().getAnswer())-1));
            if(!userAns.getText().toString().equals(ans.getText().toString())){
                userAns.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                correct++;
                continue;
            }
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
            View view = inflater.inflate(R.layout.line, null);
            scrollViewLinearLayout.addView(ques);
            scrollViewLinearLayout.addView(userAns);
            scrollViewLinearLayout.addView(ans);
            if(i==questions.size()-1)
                break;
            scrollViewLinearLayout.addView(view);
        }
        ProgressBar pB = (ProgressBar) findViewById(R.id.progressBar2);
        pB.setMax(questions.size());
        pB.setProgress(correct);
        TextView tV = (TextView)findViewById(R.id.textView4);
        int a=(int) (correct*100)/questions.size();
        tV.setText(a+"%");
    }
}
