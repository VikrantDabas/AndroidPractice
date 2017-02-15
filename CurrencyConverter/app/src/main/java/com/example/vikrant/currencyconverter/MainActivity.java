/*
Homework 1
MainActivity.java
Vikrant Dabas
 */
package com.example.vikrant.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText et;
    RadioButton radioFrom=null;
    RadioButton radioTo=null;
    ArrayList<Currency> currencies = new ArrayList<Currency>();
    /* REGEX to check if string is parsable as double or not. From Official Double.valueOf(String) documentation. */
    final String Digits     = "(\\p{Digit}+)";
    final String HexDigits  = "(\\p{XDigit}+)";
    final String Exp        = "[eE][+-]?"+Digits;
    final String fpRegex    =
            ("[\\x00-\\x20]*"+ "[+-]?(" + "NaN|" + "Infinity|" + "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
                    "(\\.("+Digits+")("+Exp+")?)|" + "((" + "(0[xX]" + HexDigits + "(\\.)?)|" +
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))" +
                    "[\\x00-\\x20]*");
    /* REGEX completed */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencies.add(new Currency("USD", 1.00));
        currencies.add(new Currency("AUD", 1.00/1.34));
        currencies.add(new Currency("GBP", 1.00/0.83));
        currencies.add(new Currency("CAD", 1.00/1.32));
        currencies.add(new Currency("INR", 1.00/68.14));
        RadioGroup rgFrom = (RadioGroup) findViewById(R.id.radioGroupFrom);
        rgFrom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1)
                    radioFrom = (RadioButton) findViewById(checkedId);
                else
                    radioFrom = null;
            }
        });
        RadioGroup rgTo = (RadioGroup) findViewById(R.id.radioGroupTo);
        rgTo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1)
                    radioTo = (RadioButton) findViewById(checkedId);
                else
                    radioTo = null;
            }
        });
        Button convert = (Button) findViewById(R.id.btnConvert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioTo==null || radioFrom==null) {
                    Toast.makeText(MainActivity.this, "Make Radio Button Selections", Toast.LENGTH_SHORT).show();
                    return;
                }
                double value;
                et = (EditText) findViewById(R.id.amountInput);
                if(Pattern.matches(fpRegex, et.getText().toString()))
                {
                    value = Double.valueOf(et.getText().toString());
                    if(value < 0.0)
                    {
                        Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double sourceRate = 0.0;
                double targetRate = 0.0;
                for(Currency cur:currencies)
                {
                    if(cur.getCurrency().equals(radioFrom.getText().toString()))
                    {
                        sourceRate = cur.getRate();
                        Log.d("Demo", sourceRate + "");
                    }
                    if(cur.getCurrency().equals(radioTo.getText().toString()))
                    {
                        targetRate = cur.getRate();
                        Log.d("Demo", targetRate + "");
                    }
                }
                double targetAmount = (value*sourceRate) / targetRate;
                TextView tv = (TextView) findViewById(R.id.textResult);
                //DecimalFormat df = new DecimalFormat("#.00");
                tv.setText(String.format( "%.2f", targetAmount ));
            }
        });
    }
    public void onCLear(View v) {
        et = (EditText)findViewById(R.id.amountInput);
        TextView tv = (TextView) findViewById(R.id.textResult);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupFrom);
        rg.clearCheck();
        rg = (RadioGroup) findViewById(R.id.radioGroupTo);
        rg.clearCheck();
        et.setText("");
        tv.setText("");

    }
}

/* Can also use the following
try
{
  Double.parseDouble(number);
}
catch(NumberFormatException e)
{
  //not a double
}
*/