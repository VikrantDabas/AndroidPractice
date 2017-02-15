/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetQuestionsAsyncTask.shareData{
    private final IntentFilter filter = new IntentFilter();
    private ArrayList<Question> questions=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        if(questions!=null){
            done(questions);
        } else if(isConectedOnline()) {
            setLayout();
        } else {
            Toast.makeText(MainActivity.this, "Internet not connected!", Toast.LENGTH_SHORT).show();
            getBaseContext().registerReceiver(myReceiver, filter);
        }

        findViewById(R.id.buttonMainExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.buttonMainStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TriviaActivity.class);
                i.putExtra("QUES", questions);
                startActivity(i);
            }
        });
    }

    private void setLayout(){
        findViewById(R.id.textViewMainLoad).setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)findViewById(R.id.progressBarMain);
        progress.setVisibility(View.VISIBLE);
        new GetQuestionsAsyncTask(MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")) {
                if(isConectedOnline()){
                    Toast.makeText(context, "Internet connected!", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(myReceiver);
                    setLayout();
                } else {
                    Toast.makeText(context, "Internet not connected!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private boolean isConectedOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            unregisterReceiver(myReceiver);
        } catch (Exception ex){
        }
    }

    @Override
    public void done(ArrayList<Question> arr) {
        findViewById(R.id.textViewMainLoad).setVisibility(View.GONE);
        findViewById(R.id.progressBarMain).setVisibility(View.GONE);
        findViewById(R.id.imageViewMain).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewMainReady).setVisibility(View.VISIBLE);
        Button btn = (Button)findViewById(R.id.buttonMainStart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this,android.R.color.holo_blue_bright));
        } else {
            btn.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        }
        btn.setEnabled(true);
        questions=arr;
        try{
            unregisterReceiver(myReceiver);
        } catch (Exception ex){
        }
    }
}
