/*
Homework 1
Currency.java
Vikrant Dabas
 */
package com.example.vikrant.currencyconverter;

/**
 * Created by Vikrant on 1/20/2017.
 */

public class Currency {
    private String currency;
    private double rate;

    public Currency(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
