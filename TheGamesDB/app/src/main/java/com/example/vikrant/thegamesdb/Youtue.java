package com.example.vikrant.thegamesdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Youtue extends AppCompatActivity {
    WebView displayYoutubeVideo;
    String url;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtue);
        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().getExtras().containsKey("URL")){
            url = getIntent().getExtras().getString("URL");
            title = getIntent().getExtras().getString("TITLE");
        } else {
            Toast.makeText(this, "Error Ocurred!", Toast.LENGTH_SHORT).show();
            finish();
        }
        String id=getUrl(url);
        if(id==null){
            Toast.makeText(this, "Unable to load video!", Toast.LENGTH_SHORT).show();
            finish();
        }

        String frameVideo = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></head><body>" +title  + "&nbsp;&copy; Trailer<br><br><iframe src=\"http://www.youtube.com/embed/" + id + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        displayYoutubeVideo = (WebView) findViewById(R.id.webViewYoutube);
        displayYoutubeVideo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        displayYoutubeVideo.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        displayYoutubeVideo.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        displayYoutubeVideo.destroy();
    }

    private String getUrl(String url){
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return (matcher.group());
        }
        return null;
    }
}
