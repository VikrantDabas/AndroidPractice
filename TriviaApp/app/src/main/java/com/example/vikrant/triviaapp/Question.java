/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/11/2017.
 */

public class Question implements Serializable{
    private String id;
    private String text;
    private String image;
    private Choices choices;
    private int userChoice;

    static public Question createQuestions(JSONObject questionsJSONObject) throws JSONException {
        Question question = new Question();
        JSONObject choicesForQuestionJSON = questionsJSONObject.getJSONObject("choices");
        Choices choicesForQuestion = Choices.createChoices(choicesForQuestionJSON);
        question.setChoices(choicesForQuestion);
        question.setId(questionsJSONObject.getString("id"));
        question.setText(questionsJSONObject.getString("text"));
        question.setUserChoice(-1);
        if(questionsJSONObject.has("image")){
            question.setImage(questionsJSONObject.getString("image"));
        }
        else{
            question.setImage("");
        }
        return question;
    }

    public Question() {
    }

    public Question(String id, String text, String image, Choices choices) {
        super();
        this.id = id;
        this.text = text;
        this.image = image;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Choices getChoices() {
        return choices;
    }

    public void setChoices(Choices choices) {
        this.choices = choices;
    }

    public int getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(int userChoice) {
        this.userChoice = userChoice;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices=" + choices +
                '}' + "\n";
    }
}
