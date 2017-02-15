/*
Vikrant Dabas
Rohit Katiyar
 */
package com.example.vikrant.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant on 2/6/2017.
 */

public class ArticleUtil {
    static public class ArticlesJSONParser {
        static ArrayList<Article> parseArticles(String in) throws JSONException {
            ArrayList<Article> listArticles = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray articlesJSONArray = root.getJSONArray("articles");
            for(int i=0; i<articlesJSONArray.length();i++){
                JSONObject articlesJSONObject = articlesJSONArray.getJSONObject(i);
                Article article= Article.createArticle(articlesJSONObject);
                listArticles.add(article);
            }
            return listArticles;
        }
    }
}
