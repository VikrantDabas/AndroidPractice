/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {
    private int position;
    private TextView questionNumber;
    private TextView timer;
    private TextView questionText;
    private ImageView questionImage;
    private RadioGroup questionGroup;
    private Button questionNext;
    private Button questionNPrev;
    private ProgressBar progressBar;
    private ArrayList<String> choice;
    private boolean flag=false;
    private int answered;
    private int temp;

    ArrayList<Question> questions = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey("QUES")){
            questions = (ArrayList<Question>) getIntent().getExtras().getSerializable("QUES");
        } else {
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            finish();
        }
        position=0;
        answered=0;
        questionNumber = (TextView) findViewById(R.id.textViewTriviaQ);
        timer = (TextView) findViewById(R.id.textViewTriviaTimer);
        questionText = (TextView) findViewById(R.id.textViewTriviaQuestion);
        questionImage = (ImageView) findViewById(R.id.imageViewTrivia);
        questionGroup = (RadioGroup) findViewById(R.id.radioGroupTrivia);
        questionNext = (Button) findViewById(R.id.buttonTriviaNext);
        questionNPrev = (Button) findViewById(R.id.buttonTriviaPrev);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTrivia);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            questionNPrev.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.darker_gray));
        } else {
            questionNPrev.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
        if(!questions.get(position).getImage().equals("")){
            progressBar.setVisibility(View.VISIBLE);
            questionImage.setVisibility(View.VISIBLE);
        }
        temp = (Integer.valueOf(questions.get(position).getId()) + 1);
        questionNumber.setText(" Q" + temp+" ");
        questionText.setText(questions.get(position).getText());
        if(!questions.get(position).getImage().equals("")){
            Picasso.with(getBaseContext())
                    .load(questions.get(position).getImage())
                    .into(questionImage,   new ImageLoadedCallback(progressBar) {
                        @Override
                        public void onSuccess() {
                            if (this.progressBar != null) {
                                this.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        } else {
            questionImage.setVisibility(View.GONE);
        }
        choice = (ArrayList<String>) questions.get(position).getChoices().getChoice();
        questionGroup.removeAllViews();
        questionGroup.clearCheck();
        for(int i=0;i<choice.size();i++){
            RadioButton rb = new RadioButton(TriviaActivity.this);
            rb.setText(choice.get(i));
            if(questions.get(position).getUserChoice()==i){
                rb.setChecked(true);
            } else {
                rb.setChecked(false);
            }
            rb.setId(i);
            questionGroup.addView(rb);
        }

        questionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==-1){
                    return;
                }
                if(questions.get(position).getUserChoice()==-1){
                    answered++;
                    if(answered==questions.size()){
                        if(position == (questions.size()-1))
                        questionNext.setText(getResources().getString(R.string.trivia_activity_finish));
                        questionNext.setEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            questionNext.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.holo_blue_bright));
                        } else {
                            questionNext.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        }
                        flag=true;
                    }
                }
                questions.get(position).setUserChoice(checkedId);  //coz id is same as position of radiobutton
                Log.d("answer", answered+"");
                Log.d("ID", checkedId+"");
            }
        });

        //To implement the timer
        final CountDownTimer cDT =  new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                millisUntilFinished /= 1000;
                timer.setText(" Time Left: " + millisUntilFinished + " seconds ");
            }

            public void onFinish() {
                Intent i=new Intent(TriviaActivity.this, StatsActivity.class);
                i.putExtra("QUES", questions);
                startActivity(i);
                finish();
            }
        };

        cDT.start();


        findViewById(R.id.buttonTriviaPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position==0){
                    questionNPrev.setEnabled(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        questionNPrev.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.darker_gray));
                    } else {
                        questionNPrev.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }
                if(position==(questions.size()-2) && !flag){
                    questionNext.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        questionNext.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.holo_blue_bright));
                    } else {
                        questionNext.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                    }
                }
                if(position<(questions.size()-1)){
                    questionNext.setText(getResources().getString(R.string.trivia_activity_next));
                }
                if(!questions.get(position).getImage().equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    questionImage.setVisibility(View.VISIBLE);
                }
                temp = (Integer.valueOf(questions.get(position).getId()) + 1);
                questionNumber.setText(" Q" + temp+" ");
                questionText.setText(questions.get(position).getText());
                if(!questions.get(position).getImage().equals("")){
                    Picasso.with(getBaseContext())
                            .load(questions.get(position).getImage())
                            .into(questionImage,   new ImageLoadedCallback(progressBar) {
                                @Override
                                public void onSuccess() {
                                    if (this.progressBar != null) {
                                        this.progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    questionImage.setVisibility(View.GONE);
                }
                choice = (ArrayList<String>) questions.get(position).getChoices().getChoice();
                questionGroup.removeAllViews();
                questionGroup.clearCheck();
                for(int i=0;i<choice.size();i++){
                    RadioButton rb = new RadioButton(TriviaActivity.this);
                    rb.setText(choice.get(i));
                    if(questions.get(position).getUserChoice()==i){
                        rb.setChecked(true);
                    } else {
                        rb.setChecked(false);
                    }
                    rb.setId(i);
                    questionGroup.addView(rb);
                }
            }
        });

        findViewById(R.id.buttonTriviaNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==(questions.size()-1) && flag){
                    Intent i=new Intent(TriviaActivity.this, StatsActivity.class);
                    i.putExtra("QUES", questions);
                    cDT.cancel();
                    startActivity(i);
                    finish();
                    return;
                }
                position++;
                if(position==(questions.size()-1) && !flag){
                    questionNext.setEnabled(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        questionNext.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.darker_gray));
                    } else {
                        questionNext.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }
                if(position==(questions.size()-1) && flag){
                    questionNext.setText(getResources().getString(R.string.trivia_activity_finish));
                }
                if(position==1){
                    questionNPrev.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        questionNPrev.setBackgroundTintList(ContextCompat.getColorStateList(TriviaActivity.this,android.R.color.holo_blue_bright));
                    } else {
                        questionNPrev.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                    }
                }
                if(!questions.get(position).getImage().equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    questionImage.setVisibility(View.VISIBLE);
                }
                temp = (Integer.valueOf(questions.get(position).getId()) + 1);
                questionNumber.setText(" Q" + temp+" ");
                questionText.setText(questions.get(position).getText());
                if(!questions.get(position).getImage().equals("")){
                    Picasso.with(getBaseContext())
                            .load(questions.get(position).getImage())
                            .into(questionImage,   new ImageLoadedCallback(progressBar) {
                                @Override
                                public void onSuccess() {
                                    if (this.progressBar != null) {
                                        this.progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    questionImage.setVisibility(View.GONE);
                }
                choice = (ArrayList<String>) questions.get(position).getChoices().getChoice();
                questionGroup.removeAllViews();
                questionGroup.clearCheck();
                for(int i=0;i<choice.size();i++){
                    RadioButton rb = new RadioButton(TriviaActivity.this);
                    rb.setText(choice.get(i));
                    if(questions.get(position).getUserChoice()==i){
                        rb.setChecked(true);
                    } else {
                        rb.setChecked(false);
                    }
                    rb.setId(i);
                    questionGroup.addView(rb);
                }
            }
        });
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
