package com.example.vikrant.thegamesdb;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/17/2017.
 */

public class AsyncDownloader extends AsyncTask<String, Void, ArrayList<GameList>> {
    shareData activity;
    Context context;

    public AsyncDownloader(shareData activity, Context context){
        this.activity=activity;
        this.context=context;
    }

    @Override
    protected ArrayList<GameList> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return GamesUtil.GamesSAXParser.parseGame(in);
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
    protected void onPostExecute(ArrayList<GameList> games) {
        super.onPostExecute(games);
        if(games!=null){
            activity.done(games);
        } else {
            activity.done(games);
        }
    }

    public interface shareData{
        void done(ArrayList<GameList> arr);
    }
}
