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

public class InformationActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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

        TextView link1 = findViewById(R.id.link1);
        TextView link2 = findViewById(R.id.link2);
        TextView link3 = findViewById(R.id.link3);
        TextView link4 = findViewById(R.id.link4);
        TextView link5 = findViewById(R.id.link5);
        TextView link6 = findViewById(R.id.link6);
        TextView link7 = findViewById(R.id.link7);

        link1.setOnClickListener(v -> openWebPage(link1.getTag().toString()));
        link2.setOnClickListener(v -> openWebPage(link2.getTag().toString()));
        link3.setOnClickListener(v -> openWebPage(link3.getTag().toString()));
        link4.setOnClickListener(v -> openWebPage(link4.getTag().toString()));
        link5.setOnClickListener(v -> openWebPage(link5.getTag().toString()));
        link6.setOnClickListener(v -> openWebPage(link6.getTag().toString()));
        link7.setOnClickListener(v -> openWebPage(link7.getTag().toString()));
    }

    private void openWebPage(String url) {
        webView.loadUrl(url);
    }
}