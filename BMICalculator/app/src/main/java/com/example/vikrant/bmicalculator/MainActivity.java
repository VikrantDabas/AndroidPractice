package com.example.vikrant.bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    /* REGEX to check if string is parsable as double or not. From Official Double.valueOf(String) documentation. */
    final String Digits     = "(\\p{Digit}+)";
    final String HexDigits  = "(\\p{XDigit}+)";
    final String Exp        = "[eE][+-]?"+Digits;
    final String fpRegex    =
            ("[\\x00-\\x20]*"+ "[+-]?(" + "NaN|" + "Infinity|" + "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
                    "(\\.("+Digits+")("+Exp+")?)|" + "((" + "(0[xX]" + HexDigits + "(\\.)?)|" +
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))" +
                    "[\\x00-\\x20]*");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
