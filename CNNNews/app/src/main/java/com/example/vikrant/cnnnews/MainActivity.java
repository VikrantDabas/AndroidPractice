package com.example.vikrant.cnnnews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetNewsAsyncTask.ShareData{
    ArrayList<NewsItem> articles = null;
    private ImageView image;
    private TextView title;
    private TextView date;
    private TextView description;
    private TextView load;
    private ProgressBar progress;
    private LinearLayout ll;
    private LinearLayout control;
    private Button btn;
    private int position;
    ScrollView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageView);
        title = (TextView) findViewById(R.id.textViewTitle);
        date = (TextView) findViewById(R.id.textViewDate);
        description = (TextView) findViewById(R.id.textViewDescBody);
        progress = (ProgressBar) findViewById(R.id.progressBar2);
        load = (TextView) findViewById(R.id.textViewLoading);
        ll = (LinearLayout) findViewById(R.id.ll);
        control = (LinearLayout) findViewById(R.id.controlBar);
        btn = (Button) findViewById(R.id.button);
        sc = (ScrollView) findViewById(R.id.scrollView);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cnn);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConectedOnline()){
                    //http://rss.cnn.com/rss/cnn_tech.rss
                    new GetNewsAsyncTask(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
                    load.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.GONE);
                    control.setVisibility(View.GONE);
                    btn.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Please connect to Internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imageButtonFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                Picasso.with(getBaseContext())
                        .load(articles.get(position).getImageURL())
                        .into(image);
                date.setText("Published on: "+articles.get(position).getPubDate());
                title.setText(articles.get(position).getTitle());
                description.setText(articles.get(position).getDescription());
            }
        });

        findViewById(R.id.imageButtonPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position ==1){
                    return;
                } else {
                    position--;
                }
                Picasso.with(getBaseContext())
                        .load(articles.get(position).getImageURL())
                        .into(image);
                date.setText("Published on: "+articles.get(position).getPubDate());
                title.setText(articles.get(position).getTitle());
                description.setText(articles.get(position).getDescription());
            }
        });

        findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == (articles.size()-1)){
                    return;
                } else {
                    position++;
                }
                Picasso.with(getBaseContext())
                        .load(articles.get(position).getImageURL())
                        .into(image);
                date.setText("Published on: "+articles.get(position).getPubDate());
                title.setText(articles.get(position).getTitle());
                description.setText(articles.get(position).getDescription());
            }
        });

        findViewById(R.id.imageButtonLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = articles.size()-1;
                Picasso.with(getBaseContext())
                        .load(articles.get(position).getImageURL())
                        .into(image);
                date.setText("Published on: "+articles.get(position).getPubDate());
                title.setText(articles.get(position).getTitle());
                description.setText(articles.get(position).getDescription());
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
    public void setupData(ArrayList<NewsItem> arr) {
        position = 1;
        articles = arr;
        load.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        control.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
        sc.setVisibility(View.VISIBLE);
        Picasso.with(getBaseContext())
                .load(articles.get(position).getImageURL())
                .into(image);
        title.setText(articles.get(position).getTitle());
        description.setText(articles.get(position).getDescription());
        date.setText("Published on: "+articles.get(position).getPubDate());
    }
}
