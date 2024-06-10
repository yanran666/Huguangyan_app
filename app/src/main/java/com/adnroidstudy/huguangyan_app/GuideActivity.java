package com.adnroidstudy.huguangyan_app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GuideActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回到上一个Activity（即MainActivity）
            }
        });

        webView = findViewById(R.id.webView);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        ImageView guide1 = findViewById(R.id.guide_img1);
        ImageView guide2 = findViewById(R.id.guide_img2);
        ImageView guide3 = findViewById(R.id.guide_img3);
        ImageView guide4 = findViewById(R.id.guide_img4);


        guide1.setOnClickListener(v -> openWebPage(guide1.getTag().toString()));
        guide2.setOnClickListener(v -> openWebPage(guide2.getTag().toString()));
        guide3.setOnClickListener(v -> openWebPage(guide3.getTag().toString()));
        guide4.setOnClickListener(v -> openWebPage(guide4.getTag().toString()));
    }

    private void openWebPage(String url) {
        webView.loadUrl(url);
    }

}