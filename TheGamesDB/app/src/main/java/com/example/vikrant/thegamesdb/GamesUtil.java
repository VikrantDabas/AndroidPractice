package com.example.vikrant.thegamesdb;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Vikrant on 2/17/2017.
 */

public class GamesUtil {
    public static class GamesSAXParser extends DefaultHandler {
        private ArrayList<GameList> gameList;
        StringBuilder xmlInnerText;
        GameList game;

        public ArrayList<GameList> getGameList() {
            return gameList;
        }

        public void setGames(ArrayList<GameList> gameList) {
            this.gameList = gameList;
        }

        public static ArrayList<GameList> parseGame(InputStream in) throws IOException, SAXException {
            GamesSAXParser parser = new GamesSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return parser.getGameList();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            gameList = new ArrayList<GameList>();
            xmlInnerText = new StringBuilder();

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("Game")){
                game = new GameList();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("Game")){
                gameList.add(game);
            } else if(localName.equals("id")){
                game.setId(xmlInnerText.toString().trim());
            } else if(localName.equals("GameTitle")){
                game.setTitle(xmlInnerText.toString().trim());
            } else if(localName.equals("ReleaseDate")){
                game.setReleaseDate(xmlInnerText.toString().trim());
            } else if(localName.equals("Platform")) {
                game.setPlatform(xmlInnerText.toString().trim());
            }
            xmlInnerText.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch, start, length);
        }
    }

    public static class GameSAXParser extends DefaultHandler {
        StringBuilder xmlInnerText;
        Game game;
        String str="";
        ArrayList<Integer> ids;
        int count=0;

        public Game getGame() {
            return game;
        }

        public static Game parseGame(InputStream in) throws IOException, SAXException {
            GameSAXParser parser = new GameSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return parser.getGame();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            game = new Game();
            xmlInnerText = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("Genres")){
                str="";
            } else if(localName.equals("Similar")){
                ids = new ArrayList<Integer>();
            } else if(localName.equals("Game")){
                count++;
            } else if(localName.equals("boxart")){
                if(game.getImageURL()==null)
                    game.setImageURL(attributes.getValue("thumb"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("baseImgUrl")){
                game.setBaseImageURL(xmlInnerText.toString().trim());
            } else if(localName.equals("GameTitle")){
                game.setTitle(xmlInnerText.toString().trim());
            } else if(localName.equals("Overview")){
                game.setOverview(xmlInnerText.toString().trim());
            } else if(localName.equals("Publisher")){
                game.setPublisher(xmlInnerText.toString().trim());
            } else if(localName.equals("genre")) {
                if(str.equals("")){
                    str += xmlInnerText.toString().trim();
                } else {
                    str = str + ", " + xmlInnerText.toString().trim();
                }
            } else if(localName.equals("Genres")){
                game.setGenres(str);
            } else if(localName.equals("Similar")){
                game.setId(ids);
            } else if(localName.equals("Game")){
                count--;
            } else if(localName.equals("id") && count>1){
                ids.add(Integer.parseInt(xmlInnerText.toString().trim()));
            } else if(localName.equals("Youtube")){
                game.setTrailerURL(xmlInnerText.toString().trim());
            } else if(localName.equals("original")){
                if(game.getImageURL()==null)
                    game.setImageURL(xmlInnerText.toString().trim());
            } else if(localName.equals("Platform")){
                game.setPlatform(xmlInnerText.toString().trim());
            } else if(localName.equals("ReleaseDate")){
                game.setReleaseDate(xmlInnerText.toString().trim());
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
