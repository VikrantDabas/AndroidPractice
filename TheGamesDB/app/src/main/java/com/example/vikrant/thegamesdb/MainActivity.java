package com.example.vikrant.thegamesdb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncDownloader.shareData{
    private Button go, search;
    private RadioGroup games;
    private ArrayList<GameList> gameList;
    private EditText key;
    private final String baseURL="http://thegamesdb.net/api/GetGamesList.php?name=";
    private final String baseURLGame="http://thegamesdb.net/api/GetGame.php?id=";
    private AsyncDownloader asyncTask;
    private String url;
    private MyProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (Button)findViewById(R.id.buttonGo);
        search = (Button) findViewById(R.id.buttonSearch);
        games = (RadioGroup) findViewById(R.id.radioGroup);
        key = (EditText) findViewById(R.id.editTextGameSearch);
        progress = new MyProgressDialog(MainActivity.this);//, R.style.AppCompatAlertDialogStyle);
        progress.setCancelable(false);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConectedOnline()){
                    if(key.getText().toString().trim()==null){
                        Toast.makeText(MainActivity.this, "Enter search term!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    go.setEnabled(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        go.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this,android.R.color.darker_gray));
                    } else {
                        go.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                    go.setTextColor(getResources().getColor(android.R.color.background_dark));
                    games.setVisibility(View.GONE);
                    progress.show();
                    asyncTask = new AsyncDownloader(MainActivity.this, getBaseContext());
                    asyncTask.execute(baseURL + key.getText().toString().trim());
                } else {
                    Toast.makeText(MainActivity.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameDetails.class);
                i.putExtra("URL", url);
                Log.d("Vicky", url);
                startActivity(i);
            }
        });

        games.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId!=-1){
                    go.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        go.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this,android.R.color.holo_blue_bright));
                    } else {
                        go.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                    }
                    go.setTextColor(getResources().getColor(android.R.color.background_light));
                    url = baseURLGame + String.valueOf(checkedId);
                }
            }
        });
    }

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
    protected void onDestroy() {
        super.onDestroy();
        if(asyncTask!=null){
            asyncTask.cancel(true);
            progress.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(asyncTask!=null){
            asyncTask.cancel(true);
            progress.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(asyncTask!=null){
            asyncTask.cancel(true);
            progress.dismiss();
        }
    }

    @Override
    public void done(ArrayList<GameList> arr) {
        if(arr==null){
            progress.dismiss();
            Toast.makeText(MainActivity.this, "Nothing Fetched! Search Again!", Toast.LENGTH_SHORT).show();
            return;
        }
        gameList = arr;
        games.setVisibility(View.VISIBLE);
        progress.dismiss();
        games.clearCheck();
        games.removeAllViews();
        for(GameList game:gameList){
            StringBuilder sb=new StringBuilder();
            sb.append(game.getTitle());
            sb.append(". Released in ");
            sb.append(game.getReleaseDate());
            sb.append(". Platform: ");
            sb.append(game.getPlatform());
            sb.append(".");
            RadioButton rb = new RadioButton(MainActivity.this);
            rb.setId(Integer.parseInt(game.getId()));
            rb.setText(sb.toString());
            rb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rb.setBackground(getResources().getDrawable(R.drawable.radio_button_border, getTheme()));
            } else {
                rb.setBackground(getResources().getDrawable(R.drawable.radio_button_border));
            }
            games.addView(rb);
        }
        TextView tV=new TextView(MainActivity.this);
        tV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tV.setPadding(20,5,5,40);
        tV.setText(" ");
        games.addView(tV);
    }

    public class MyProgressDialog extends AlertDialog{

        protected MyProgressDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        public void show() {
            super.show();
            setContentView(R.layout.progress_dialog);
        }

    }
}
