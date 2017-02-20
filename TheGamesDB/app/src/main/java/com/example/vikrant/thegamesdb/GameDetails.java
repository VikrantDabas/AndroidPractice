package com.example.vikrant.thegamesdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameDetails extends AppCompatActivity implements AsyncDownloaderGame.shareData{
    private TextView title, overview, genre, publisher;
    private AsyncDownloaderGame asyncTask;
    private Button trailer, similar, finish;
    private MyProgressDialog progress;
    private ImageView image;
    private String url="";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey("URL")){
            url = getIntent().getExtras().getString("URL");
        } else {
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            finish();
        }
        progress = new MyProgressDialog(GameDetails.this);
        progress.setCancelable(false);
        progress.show();
        asyncTask = new AsyncDownloaderGame(GameDetails.this);
        asyncTask.execute(url);
        title = (TextView) findViewById(R.id.textViewTitle);
        overview = (TextView) findViewById(R.id.textViewOverview);
        genre = (TextView) findViewById(R.id.textViewGenre);
        image = (ImageView) findViewById(R.id.imageView);
        publisher = (TextView) findViewById(R.id.textViewPublisher);
        trailer = (Button) findViewById(R.id.buttonPlay);
        similar = (Button) findViewById(R.id.buttonSimilar);
        finish = (Button) findViewById(R.id.buttonFinish);
        overview.setMovementMethod(new ScrollingMovementMethod());

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.getTrailerURL()==null){
                    Toast.makeText(GameDetails.this, "Sorry, no trailer for this game!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(GameDetails.this, Youtue.class);
                i.putExtra("URL", game.getTrailerURL());
                i.putExtra("TITLE", game.getTitle());
                Log.d("Vicky", game.getTrailerURL());
                startActivity(i);
            }
        });

        similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.getId()==null || game.getId().size()==0){
                    Toast.makeText(GameDetails.this, "Sorry, no similar games!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i=new Intent(GameDetails.this, SimilarActivity.class);
                i.putExtra("IDS", game.getId());
                i.putExtra("TITLE", game.getTitle());
                startActivity(i);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void done(Game obj) {
        if(obj==null){
            Toast.makeText(this, "Error Ocurred! Trying Again!", Toast.LENGTH_SHORT).show();
            new AsyncDownloaderGame(GameDetails.this).execute(url);
            return;
        }
        progress.dismiss();
        game=obj;
        title.setText(obj.getTitle());
        overview.setText(obj.getOverview());
        genre.setText("Genre: " + obj.getGenres());
        publisher.setText("Publisher: " + obj.getPublisher());
        if(obj.getImageURL()!=null)
            Picasso.with(getBaseContext()).load(obj.getBaseImageURL()+obj.getImageURL()).placeholder(R.drawable.dummyimage).into(image);
    }

    public class MyProgressDialog extends AlertDialog {

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
