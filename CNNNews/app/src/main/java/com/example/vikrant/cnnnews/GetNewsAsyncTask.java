package com.example.vikrant.cnnnews;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/13/2017.
 */

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
    ShareData activity;

    public GetNewsAsyncTask(ShareData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return NewsItemUtil.ArticleSAXParser.parseArticle(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> articles) {
        super.onPostExecute(articles);
        if(articles!=null){
            Log.d("RESULT", articles.size()+"");
            activity.setupData(articles);
        } else {
            Log.d("RESULT", "EMPTY!");
        }
    }

    public interface ShareData{
        void setupData(ArrayList<NewsItem> arr);
    }
}
