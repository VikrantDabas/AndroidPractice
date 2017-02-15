/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant on 2/11/2017.
 */

public class ParsingQuestionUtility {
    static public class QuestionsJSONParser {
        static ArrayList<Question> parseQuestions(String in) throws JSONException {
            ArrayList<Question> listQuestions = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray questionJSONArray = root.getJSONArray("questions");
            for(int i=0; i<questionJSONArray.length();i++){
                JSONObject questionJSON = questionJSONArray.getJSONObject(i);
                Question question = Question.createQuestions(questionJSON);
                listQuestions.add(question);
            }
            return listQuestions;
        }
    }
}
