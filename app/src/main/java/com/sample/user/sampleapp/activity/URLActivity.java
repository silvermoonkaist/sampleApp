package com.sample.user.sampleapp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sample.user.sampleapp.R;

import java.io.UnsupportedEncodingException;

public class URLActivity extends AppCompatActivity {
    private String URL;
    private String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        URL = bundle.getString("URL");
        imageURL = bundle.getString("imageURL");
        WebView webview = (WebView)findViewById(R.id.webView);
        WebSettings set = webview.getSettings();
        webview.setWebViewClient(new WebViewClient());
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        Toast.makeText(this,imageURL,Toast.LENGTH_SHORT).show();
        Log.d("xxx",imageURL);
        try {
            webview.loadUrl(java.net.URLDecoder.decode(URL, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
