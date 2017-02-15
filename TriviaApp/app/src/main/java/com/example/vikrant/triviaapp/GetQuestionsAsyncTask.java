/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/11/2017.
 */

public class GetQuestionsAsyncTask extends AsyncTask<String, Void, ArrayList<Question>> {
    shareData activity;

    public GetQuestionsAsyncTask(shareData activity){
        this.activity=activity;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        BufferedReader br=null;
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while(line!=null){
                    sb.append(line);
                    line = br.readLine();
                }
                return ParsingQuestionUtility.QuestionsJSONParser.parseQuestions(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        if(questions != null){
            activity.done(questions);
            Log.d("Questions", questions.toString());
        }
    }

    public interface shareData{
        void done(ArrayList<Question> arr);
    }
}
