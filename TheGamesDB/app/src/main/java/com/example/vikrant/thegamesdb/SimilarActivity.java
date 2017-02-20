package com.example.vikrant.thegamesdb;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SimilarActivity extends AppCompatActivity implements AsyncDownloaderGame.shareData{
    private ArrayList<Integer> arr;
    private final String baseURLGame="http://thegamesdb.net/api/GetGame.php?id=";
    private TextView title;
    private int count=0;
    private ProgressBar progress;
    private LinearLayout similarGames;
    private ArrayList<AsyncDownloaderGame> asyncTasks=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);
        String titleText="TITLE";
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey("IDS")){
            arr = (ArrayList<Integer>) getIntent().getExtras().getSerializable("IDS");
            titleText = getIntent().getExtras().getString("TITLE");
        }
        title = (TextView)findViewById(R.id.textViewHeadingSimilar);
        progress = (ProgressBar) findViewById(R.id.progressBar2);
        title.setText(getResources().getString(R.string.label_heading_similar) + " " + titleText);
        similarGames = (LinearLayout) findViewById(R.id.linearLayoutSimilar);
        similarGames.removeAllViews();
        count=0;
        progress.setVisibility(View.VISIBLE);
        for(Integer i:arr){
            asyncTasks.add(new AsyncDownloaderGame(SimilarActivity.this));
            asyncTasks.get(asyncTasks.size()-1).execute(baseURLGame + String.valueOf(i));
        }

        findViewById(R.id.buttonFinishSimilar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(AsyncDownloaderGame g:asyncTasks){
            g.cancel(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for(AsyncDownloaderGame g:asyncTasks){
            g.cancel(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for(AsyncDownloaderGame g:asyncTasks){
            g.cancel(true);
        }
    }

    @Override
    public void done(Game obj) {
        TextView tV=new TextView(SimilarActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tV.setBackground(getResources().getDrawable(R.drawable.radio_button_border, getTheme()));
        } else {
            tV.setBackground(getResources().getDrawable(R.drawable.radio_button_border));
        }
        tV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tV.setPadding(20,5,5,40);
        if(obj==null){
            Toast.makeText(this, "Error Ocurred! Connection Lost, some data couldn't be downloaded!", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb=new StringBuilder();
            sb.append(obj.getTitle());
            sb.append(". Released in ");
            sb.append(obj.getReleaseDate());
            sb.append(". Platform: ");
            sb.append(obj.getPlatform());
            sb.append(".");
            tV.setText(sb.toString());
        }
        count++;
        similarGames.addView(tV);
        if(count==arr.size()){
            progress.setVisibility(View.GONE);
        }
    }
}
