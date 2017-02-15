package com.example.vikrant.cnnnews;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/13/2017.
 */

public class NewsItemUtil {
        public static class ArticleSAXParser extends DefaultHandler {
            ArrayList<NewsItem> articleList;
            StringBuilder xmlInnerText;
            NewsItem article;
            boolean flag = false;
            int count=0;

            public ArrayList<NewsItem> getArticleList() {
                return articleList;
            }

            public static ArrayList<NewsItem> parseArticle(InputStream in) throws IOException, SAXException {
                ArticleSAXParser parser = new ArticleSAXParser();
                Xml.parse(in, Xml.Encoding.UTF_8, parser);
                return parser.getArticleList();
            }

            @Override
            public void startDocument() throws SAXException {
                super.startDocument();
                articleList = new ArrayList<NewsItem>();

                xmlInnerText = new StringBuilder();

            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if(localName.equals("title")){
                    article = new NewsItem();

                } else if(qName.equals("media:content")){
                    if(attributes.getValue("height").trim().equals(attributes.getValue("width").trim())){
                        article.setImageURL(attributes.getValue("url"));
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
                if(localName.equals("item")){

                    articleList.add(article);

                } else if(localName.equals("title")){

                    article.setTitle(xmlInnerText.toString().trim());
                } else if(localName.equals("description")){

                    article.setDescription(xmlInnerText.toString().trim());
                    articleList.add(article);
                } else if(localName.equals("pubDate")){

                    article.setPubDate(xmlInnerText.toString().trim());
                    Log.d("asdf", xmlInnerText.toString().trim());
                }
                xmlInnerText.setLength(0);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
                xmlInnerText.append(ch, start, length);
            }
        }
    }

