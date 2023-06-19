package com.example.mytrailer;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ReproductorVideoActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_video);

        webView = findViewById(R.id.webView);

        String videoUrl = getIntent().getStringExtra("videoUrl");

        if (videoUrl != null) {
            String html = "<html><body><video width=\"100%\" height=\"100%\" controls autoplay><source src=\"" + videoUrl + "\" type=\"video/mp4\"></video></body></html>";

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    }
}



