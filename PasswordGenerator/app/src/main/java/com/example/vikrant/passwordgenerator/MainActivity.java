/*
Vikrant Dabas: 800936479
Neeraj Kumar Nittoori: 800953520
*/
package com.example.vikrant.passwordgenerator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    ProgressDialog progress;
    ExecutorService threadPool;
    SeekBar skThreadCount, skThreadLength, skTaskCount, skTaskLength;
    ArrayList<String> passwordsThread = new ArrayList<String>();
    ArrayList<String> passwordsAsync = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skThreadCount = (SeekBar)findViewById(R.id.seekBar);
        skThreadLength = (SeekBar)findViewById(R.id.seekBar2);
        skTaskCount = (SeekBar)findViewById(R.id.seekBar3);
        skTaskLength = (SeekBar)findViewById(R.id.seekBar4);
        skThreadCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tV = (TextView)findViewById(R.id.tCount);
                tV.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skThreadLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tV = (TextView)findViewById(R.id.tLength);
                tV.setText(String.valueOf(progress + 7));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skTaskCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tV = (TextView)findViewById(R.id.aCount);
                tV.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skTaskLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tV = (TextView)findViewById(R.id.aLength);
                tV.setText(String.valueOf(progress + 7));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 0x11:
                        int p = progress.getProgress();
                        progress.setProgress(p+1);
                        String a = msg.getData().getString("done");
                        passwordsThread.add(a);
                        if(progress.getProgress() == (skTaskCount.getProgress() + skThreadCount.getProgress() + 2)){
                            Intent i = new Intent(MainActivity.this, SecondActivity.class);
                            i.putStringArrayListExtra("thread", passwordsThread);
                            i.putStringArrayListExtra("async", passwordsAsync);
                            progress.dismiss();
                            startActivity(i);
                        }
                        break;
                }
                return false;
            }
        });
        threadPool = Executors.newFixedThreadPool(2);

        findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordsAsync.clear();
                passwordsThread.clear();
                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Generating Passwords");
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setMax(skTaskCount.getProgress() + skThreadCount.getProgress() + 2);
                progress.show();
                for(int i=0;i<skThreadCount.getProgress()+1;i++){
                    threadPool.execute(new DoWorkThread(skThreadLength.getProgress()+7));
                }
                for(int i=0;i<skTaskCount.getProgress()+1;i++){
                    new DoWorkAtask().execute(skTaskLength.getProgress()+7);
                }

            }
        });

    }

    class DoWorkThread implements Runnable {
        Message msg;
        private int length;
        public DoWorkThread(int length) {
            this.length = length;
        }

        @Override
        public void run() {
            String pwd;
            pwd = util.getPassword(length);
            msg = new Message();
            msg.what = 0x11;
            Bundle data = new Bundle();
            data.putString("done", pwd);
            msg.setData(data);
            handler.sendMessage(msg);
            /*
            for(int i=0;i<count;i++){
                pwd = util.getPassword(length);
                msg = new Message();
                msg.what = 0x11;
                msg.obj = i+1;
                Bundle data = new Bundle();
                data.putString("done", pwd);
                msg.setData(data);
                handler.sendMessage(msg);
            }*/
        }
    }

    class DoWorkAtask extends AsyncTask<Integer, String, ArrayList<String>>{
        private int count, length;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            int p = progress.getProgress();
            progress.setProgress(p+1);
            passwordsAsync.add(values[0]);
            if(progress.getProgress() == (skTaskCount.getProgress() + skThreadCount.getProgress() + 2)){
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putStringArrayListExtra("thread", passwordsThread);
                i.putStringArrayListExtra("async", passwordsAsync);
                progress.dismiss();
                startActivity(i);
            }
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            this.length = params[0];
            String pwd = util.getPassword(length);
            publishProgress(pwd);
            return null;
        }
    }
}
