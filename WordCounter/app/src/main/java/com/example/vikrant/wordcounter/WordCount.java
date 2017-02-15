/*
Vikrant Dabas
Rohit Katiyar
*/
package com.example.vikrant.wordcounter;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Vikrant on 2/5/2017.
 */

class WordCount
{
    public WordCount() {
    }

    public static int countWord(String word, boolean matchCase, Context myContext) {
        AssetManager mngr = myContext.getAssets();

        int count = 0;
        try{
            InputStream is = mngr.open("textfile.txt");
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                Scanner s2 = new Scanner(scanner.nextLine());
                while(s2.hasNext()){
                    String nextToken = s2.next();

                    nextToken = nextToken.replaceAll("[^a-zA-Z ]","");

                    if(matchCase == false)
                    {
                        if (nextToken.equalsIgnoreCase(word)){
                            Log.d("VDHAPPY", word);
                            count++;
                        }

                    }
                    else
                    {
                        if (nextToken.equals(word))
                            count++;
                    }
                }
            }

        }
        catch(Exception fn)
        {
            fn.printStackTrace();
            return -1;
        }
        return count;
    }
}