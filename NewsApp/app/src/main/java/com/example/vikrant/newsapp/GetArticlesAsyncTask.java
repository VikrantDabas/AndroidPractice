/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.newsapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/6/2017.
 */

public class GetArticlesAsyncTask extends AsyncTask<String, Void, ArrayList<Article>> {
    ShareData activity;

    public GetArticlesAsyncTask(ShareData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        BufferedReader reader=null;
        try {
            URL url = new URL(params[0]);
            //https://newsapi.org/v1/articles?apiKey=d5ae3136992e428c8da11c5cc5baeae6&source=bbc-news
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line!=null){
                    sb.append(line);
                    line = reader.readLine();
                }
                Log.d("Demo", sb.toString());
                return ArticleUtil.ArticlesJSONParser.parseArticles(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        if(articles!=null){
            Log.d("ArrayList", articles.toString());
            activity.setupData(articles);
        }
    }

    public interface ShareData{
        void setupData(ArrayList<Article> arr);
    }
}
