/*
Vikrant Dabas
Rohit Katiyar
*/
package com.example.vikrant.wordcounter;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Vikrant on 2/5/2017.
 */

public class Test {
    EditText et;
    ImageButton ib;
    LinearLayout ll;

    public Test(EditText et, ImageButton ib, LinearLayout ll) {
        this.et = et;
        this.ib = ib;
        this.ll = ll;
    }

    public EditText getEt() {
        return et;
    }

    public void setEt(EditText et) {
        this.et = et;
    }

    public ImageButton getIb() {
        return ib;
    }

    public void setIb(ImageButton ib) {
        this.ib = ib;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setLl(LinearLayout ll) {
        this.ll = ll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;

        Test test = (Test) o;

        if (!getEt().equals(test.getEt())) return false;
        if (!getIb().equals(test.getIb())) return false;
        return getLl().equals(test.getLl());

    }

    @Override
    public int hashCode() {
        int result = getEt().hashCode();
        result = 31 * result + getIb().hashCode();
        result = 31 * result + getLl().hashCode();
        return result;
    }
}
