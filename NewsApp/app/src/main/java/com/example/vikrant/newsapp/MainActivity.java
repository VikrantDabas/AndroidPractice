/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.rating;

public class MainActivity extends AppCompatActivity implements GetArticlesAsyncTask.ShareData {
    ArrayList<Article> arrayArticles=null;
    String newsSelect;
    ImageView iVNews;
    TextView title;
    TextView author;
    TextView published;
    TextView description;
    SpannableStringBuilder sb;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb = new SpannableStringBuilder();
        final StyleSpan bss = new StyleSpan(Typeface.BOLD);
        iVNews = (ImageView)findViewById(R.id.imageViewNews);
        title = (TextView)findViewById(R.id.textViewTitle);
        author = (TextView)findViewById(R.id.textViewAuthor);
        published = (TextView)findViewById(R.id.textViewPublished);
        description = (TextView)findViewById(R.id.textViewDesc2);
        iVNews.setImageResource(0);

        final Spinner dropDownNews = (Spinner) findViewById(R.id.spinnerNewsChannels);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.newsArray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownNews.setAdapter(adapter);
        dropDownNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newsSelect = parent.getItemAtPosition(position).toString();
                //Toast.makeText(MainActivity.this, newsSelect,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.buttonGetNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConectedOnline()){
                    String getSelectedNews = dropDownNews.getSelectedItem().toString();
                    String url="https://newsapi.org/v1/articles?apiKey=d5ae3136992e428c8da11c5cc5baeae6&source=";
                    if(getSelectedNews.compareTo("BBC") == 0)
                    {
                        url += "bbc-news";
                    }
                    else if(getSelectedNews.compareTo("CNN") == 0)
                    {
                        url += "cnn";
                    }
                    else if(getSelectedNews.compareTo("BuzzFeed") == 0)
                    {
                        url += "buzzfeed";
                    }
                    else if(getSelectedNews.compareTo("ESPN") == 0)
                    {
                        url += "espn";
                    }
                    else if(getSelectedNews.compareTo("Sky News") == 0)
                    {
                        url += "sky-news";
                    }
                    //https://newsapi.org/v1/articles?apiKey=d5ae3136992e428c8da11c5cc5baeae6&source=bbc-news
                    if(!url.equals("https://newsapi.org/v1/articles?apiKey=d5ae3136992e428c8da11c5cc5baeae6&source=")){
                        new GetArticlesAsyncTask(MainActivity.this).execute(url);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please ensure you have a working Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.imageViewFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayArticles==null || arrayArticles.size()==0){
                    Toast.makeText(MainActivity.this, "No news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(position==0){
                    Toast.makeText(MainActivity.this, "Already viewing first news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                position = 0;
                iVNews.setImageResource(0);
                new GetImage().execute(arrayArticles.get(position).getUrlToImage().toString());

                sb.clear();
                sb.append(arrayArticles.get(position).getTitle());
                sb.setSpan(bss, 0, arrayArticles.get(position).getTitle().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(sb);

                sb.clear();
                sb.append("Author: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getAuthor());
                author.setText(sb);

                sb.clear();
                sb.append("Published on: ");
                sb.setSpan(bss, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getPublishedAt());
                published.setText(sb);

                sb.clear();
                sb.append(arrayArticles.get(position).getDescription());
                description.setText(sb);
            }
        });

        findViewById(R.id.imageViewLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayArticles==null || arrayArticles.size()==0){
                    Toast.makeText(MainActivity.this, "No news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(position==arrayArticles.size()-1){
                    Toast.makeText(MainActivity.this, "Already viewing last news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                position = arrayArticles.size()-1;
                iVNews.setImageResource(0);
                new GetImage().execute(arrayArticles.get(position).getUrlToImage().toString());

                sb.clear();
                sb.append(arrayArticles.get(position).getTitle());
                sb.setSpan(bss, 0, arrayArticles.get(position).getTitle().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(sb);

                sb.clear();
                sb.append("Author: ");
                sb.append(arrayArticles.get(position).getAuthor());
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                author.setText(sb);

                sb.clear();
                sb.append("Published on: ");
                sb.setSpan(bss, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getPublishedAt());
                published.setText(sb);

                sb.clear();
                sb.append(arrayArticles.get(position).getDescription());
                description.setText(sb);
            }
        });

        findViewById(R.id.imageViewNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayArticles==null || arrayArticles.size()==0){
                    Toast.makeText(MainActivity.this, "No news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(position == arrayArticles.size()-1){
                    Toast.makeText(MainActivity.this, "No next news!", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    position++;
                }
                iVNews.setImageResource(0);
                new GetImage().execute(arrayArticles.get(position).getUrlToImage().toString());

                sb.clear();
                sb.append(arrayArticles.get(position).getTitle());
                sb.setSpan(bss, 0, arrayArticles.get(position).getTitle().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(sb);

                sb.clear();
                sb.append("Author: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getAuthor());
                author.setText(sb);

                sb.clear();
                sb.append("Published on: ");
                sb.setSpan(bss, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getPublishedAt());
                published.setText(sb);

                sb.clear();
                sb.append(arrayArticles.get(position).getDescription());
                description.setText(sb);
            }
        });

        findViewById(R.id.imageViewPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayArticles==null || arrayArticles.size()==0){
                    Toast.makeText(MainActivity.this, "No news!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(position == 0){
                    Toast.makeText(MainActivity.this, "No previous news!", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    position--;
                }
                iVNews.setImageResource(0);
                new GetImage().execute(arrayArticles.get(position).getUrlToImage().toString());

                sb.clear();
                sb.append(arrayArticles.get(position).getTitle());
                sb.setSpan(bss, 0, arrayArticles.get(position).getTitle().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(sb);

                sb.clear();
                sb.append("Author: ");
                sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getAuthor());
                author.setText(sb);

                sb.clear();
                sb.append("Published on: ");
                sb.setSpan(bss, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(arrayArticles.get(position).getPublishedAt());
                published.setText(sb);

                sb.clear();
                sb.append(arrayArticles.get(position).getDescription());
                description.setText(sb);
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
    public void setupData(ArrayList<Article> arr) {
        arrayArticles=arr;
        if(arrayArticles.size() == 0){
            Toast.makeText(this, "No news Available!", Toast.LENGTH_SHORT).show();
            return;
        }
        final StyleSpan bss = new StyleSpan(Typeface.BOLD);
        TextView description2 = (TextView)findViewById(R.id.textViewDesc);
        sb.clear();
        sb.append("Description:");
        sb.setSpan(bss, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        description2.setText(sb);

        position = 0;
        iVNews.setImageResource(0);
        new GetImage().execute(arrayArticles.get(position).getUrlToImage().toString());

        sb.clear();
        sb.append(arrayArticles.get(position).getTitle());
        sb.setSpan(bss, 0, arrayArticles.get(position).getTitle().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        title.setText(sb);

        sb.clear();
        sb.append("Author: ");
        sb.setSpan(bss, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(arrayArticles.get(position).getAuthor());
        author.setText(sb);

        sb.clear();
        sb.append("Published on: ");
        sb.setSpan(bss, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.append(arrayArticles.get(position).getPublishedAt());
        published.setText(sb);

        sb.clear();
        sb.append(arrayArticles.get(position).getDescription());
        description.setText(sb);
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in=null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                in = con.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(in);
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(in!=null){
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null){
                iVNews.setImageBitmap(bitmap);
            }
        }
    }
}
