/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/11/2017.
 */

public class Choices implements Serializable{
    private ArrayList<String> choice = null;
    private String answer;

    static public Choices createChoices(JSONObject choicesJSONObject) throws JSONException {
        Choices choice = new Choices();
        choice.setAnswer(choicesJSONObject.getString("answer"));
        JSONArray arrJSON = choicesJSONObject.getJSONArray("choice");
        ArrayList<String> arr = new ArrayList<String>();
        for(int i=0;i<arrJSON.length();i++){
            arr.add(arrJSON.getString(i));
        }
        choice.setChoice(arr);
        return choice;
    }

    public Choices() {
    }

    public Choices(ArrayList<String> choice, String answer) {
        super();
        this.choice = choice;
        this.answer = answer;
    }

    public ArrayList<String> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<String> choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Choices{" +
                "choice=" + choice +
                ", answer='" + answer + '\'' +
                '}';
    }
}
