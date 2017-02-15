/*
Vikrant Dabas
Rohit Katiyar
*/
package com.example.vikrant.wordcounter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.drawable.presence_busy;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    int uniqueID=0;
    ArrayList<Test> store = new ArrayList<Test>();
    HashMap<String, Integer> hm;
    ProgressDialog progress;
    int dpInPx;
    int words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, dm);  //dpInPx can be float
        LinearLayout scrollViewLinearLayout = (LinearLayout) findViewById(R.id.dynamic);
        scrollViewLinearLayout.removeAllViews();
        store.clear();

        //Creating Initial EditText
        LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setId(uniqueID);
        uniqueID++;
        EditText et = new EditText(MainActivity.this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f);
        et.setLayoutParams(param);
        et.setSingleLine();
        et.setId(uniqueID);
        et.setEms(10);
        uniqueID++;
        ImageButton ib = new ImageButton(MainActivity.this);
        param = new LinearLayout.LayoutParams(dpInPx , dpInPx, 1.0f);
        //param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        ib.setImageResource(R.drawable.plus_circle);
        ib.setLayoutParams(param);
        ib.setId(uniqueID);
        ib.setOnClickListener(addWordField);
        uniqueID++;
        Test obj = new Test(et, ib, ll);
        store.add(obj);
        ll.addView(et);
        ll.addView(ib);
        scrollViewLinearLayout.addView(ll);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] arr = new String[store.size()];
                for(int i=0; i<store.size();i++){
                    if(!store.get(i).getEt().getText().toString().trim().equals("")){
                        arr[i] = store.get(i).getEt().getText().toString().trim();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please Enter Valid Words in all Text Boxes", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                progress = new ProgressDialog(MainActivity.this);
                progress.setProgress(0);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(false);
                progress.setMax(store.size());
                progress.setCancelable(false);
                progress.setProgress(1);
                words = 0;
                CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
                Boolean flag = cb.isChecked();
                progress.show();
                for(String t:arr){
                    new CountWordAsyncTask().execute(t, flag.toString());
                }
            }
        });
    }

    View.OnClickListener addWordField = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout scrollViewLinearLayout = (LinearLayout) findViewById(R.id.dynamic);
            ImageButton ibOld = (ImageButton) findViewById(v.getId());
            ibOld.setImageResource(R.drawable.minus_circle);
            ibOld.setOnClickListener(removeWordField);
            LinearLayout llNew = new LinearLayout(MainActivity.this);
            llNew.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llNew.setOrientation(LinearLayout.HORIZONTAL);
            llNew.setId(uniqueID);
            uniqueID++;
            EditText etNew = new EditText(MainActivity.this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f);
            etNew.setLayoutParams(param);
            etNew.setSingleLine();
            etNew.setId(uniqueID);
            etNew.setEms(10);
            uniqueID++;
            ImageButton ibNew = new ImageButton(MainActivity.this);
            param = new LinearLayout.LayoutParams(dpInPx , dpInPx, 1.0f);
            //param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            ibNew.setLayoutParams(param);
            ibNew.setId(uniqueID);
            uniqueID++;
            if(store.size() == 19){
                ibNew.setImageResource(R.drawable.minus_circle);
                ibNew.setOnClickListener(removeWordField);
            }
            else{
                ibNew.setImageResource(R.drawable.plus_circle);
                ibNew.setOnClickListener(addWordField);
            }
            Test obj = new Test(etNew, ibNew, llNew);
            store.add(obj);
            llNew.addView(etNew);
            llNew.addView(ibNew);
            scrollViewLinearLayout.addView(llNew);
        }
    };

    View.OnClickListener removeWordField = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout ll = null;
            ImageButton ib=null;
            EditText et=null;
            Test delete = null;
            for(Test t:store){
                if(t.getIb().getId() == v.getId()){
                    ib = (ImageButton) findViewById(v.getId());
                    et = (EditText) findViewById(t.getEt().getId());
                    ll = (LinearLayout) findViewById(t.getLl().getId());
                    delete = t;
                    break;
                }
            }
            LinearLayout scrollViewLinearLayout = (LinearLayout) findViewById(R.id.dynamic);
            if(store.size()==20 && et!=null && ib!=null && ll!=null){
                ll.removeAllViews();
                scrollViewLinearLayout.removeView(ll);
                store.remove(delete);
                delete = store.get(store.size()-1);
                delete.getIb().setImageResource(R.drawable.plus_circle);
                delete.getIb().setOnClickListener(addWordField);
            }
            else if(et!=null && ib!=null && ll!=null){
                ll.removeAllViews();
                scrollViewLinearLayout.removeView(ll);
                store.remove(delete);
            }
            else{
                Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onRestart() {
        super.onRestart();
        LinearLayout scrollViewLinearLayout = (LinearLayout) findViewById(R.id.dynamic);
        scrollViewLinearLayout.removeAllViews();
        store.clear();

        //Creating Initial EditText
        LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setId(uniqueID);
        uniqueID++;
        EditText et = new EditText(MainActivity.this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f);
        et.setLayoutParams(param);
        et.setSingleLine();
        et.setId(uniqueID);
        et.setEms(10);
        uniqueID++;
        ImageButton ib = new ImageButton(MainActivity.this);
        param = new LinearLayout.LayoutParams(dpInPx , dpInPx, 1.0f);
        //param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        ib.setImageResource(R.drawable.plus_circle);
        ib.setLayoutParams(param);
        ib.setId(uniqueID);
        ib.setOnClickListener(addWordField);
        uniqueID++;
        Test obj = new Test(et, ib, ll);
        store.add(obj);
        ll.addView(et);
        ll.addView(ib);
        scrollViewLinearLayout.addView(ll);
        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        if(cb.isChecked()){
            cb.setChecked(false);
        }
    }

    class CountWordAsyncTask extends AsyncTask<String, String, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> arr = new ArrayList<String>();
            String word = params[0];
            Boolean flag = Boolean.valueOf(params[1]);
            int count = WordCount.countWord(word, flag, MainActivity.this);
            arr.add(word);
            arr.add(String.valueOf(count));
            publishProgress();
            return arr;
        }

        public CountWordAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hm = new HashMap<String, Integer>(store.size());
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            String word = strings.get(0);
            int count = Integer.valueOf(strings.get(1));
            hm.put(word, count);
            int a = progress.getProgress();
            a++;
            progress.setProgress(a);
            Log.d("Vikrant", progress.getProgress()+"");
            words++;
            if(words == progress.getMax()){
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("result", hm);
                progress.dismiss();
                startActivity(i);
            }
        }
    }

}
