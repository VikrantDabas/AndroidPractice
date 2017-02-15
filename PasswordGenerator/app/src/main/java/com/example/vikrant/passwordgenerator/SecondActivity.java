/*
Vikrant Dabas: 800936479
Neeraj Kumar Nittoori: 800953520
*/
package com.example.vikrant.passwordgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ArrayList<String> threadPasswords = new ArrayList<String>();
    ArrayList<String> asyncPasswords = new ArrayList<String>();
    LinearLayout threadLayout;
    LinearLayout asyncLayout;
    Intent myintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(getIntent()!=null)
        {
            myintent =  getIntent();
        }
        else{
            finish();
        }
        threadLayout = (LinearLayout) findViewById(R.id.threadLinearLayout);
        asyncLayout = (LinearLayout) findViewById(R.id.asyncLinearLayout);



        threadPasswords = myintent.getStringArrayListExtra("thread");
        asyncPasswords = myintent.getStringArrayListExtra("async");
        int threadPassCount = threadPasswords.size();
        int asyncPassCount = asyncPasswords.size();

        for (int i = 0;i<threadPassCount;i++)
        {
            TextView threadPasswordTextView = new TextView(this);
            threadPasswordTextView.setText(threadPasswords.get(i));
            threadPasswordTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            //  threadPasswordTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayou));
            threadLayout.addView(threadPasswordTextView);
        }
        for (int i = 0;i<asyncPassCount;i++)
        {
            TextView asyncPasswordTextView = new TextView(this);
            asyncPasswordTextView.setText(asyncPasswords.get(i));
            asyncPasswordTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            asyncLayout.addView(asyncPasswordTextView);

        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
