package com.adnroidstudy.huguangyan_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.adnroidstudy.huguangyan_app.adapter.BottomCarouselAdapter;
import com.adnroidstudy.huguangyan_app.adapter.MyPagerAdapter;
import com.adnroidstudy.huguangyan_app.adapter.TopCarouselAdapter;
import com.adnroidstudy.huguangyan_app.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Integer> images = new ArrayList<>();
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private TextView scenicTitleTextView;
    private TextView scenicContentTextView;
    private DatabaseHelper dbHelper;
    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    private LinearLayoutManager topLinearLayoutManager;
    private TopCarouselAdapter topAdapter;
    private int currentTopPosition = 0;
    private List<Integer> topImageResIds;
    private Timer topTimer;
    private final long TOP_DELAY_MS = 500; // 延迟时间
    private final long TOP_PERIOD_MS = 3000; // 周期时间
    private LinearLayoutManager bottomLinearLayoutManager;
    private BottomCarouselAdapter bottomAdapter;
    private List<Integer> bottomImageResIds;
    private int currentBottomPosition = 0;

    private Timer bottomTimer;
    private final long BOTTOM_DELAY_MS = 500; // 延迟时间
    private final long BOTTOM_PERIOD_MS = 3000; // 周期时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Button introButton = findViewById(R.id.nav_button2);
        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SynopsisActivity.class);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.view_pager);

        images.add(R.drawable.slider_image1);
        images.add(R.drawable.slider_image2);

        MyPagerAdapter adapter = new MyPagerAdapter(this, images);
        viewPager.setAdapter(adapter);

        // 自动轮播
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 设置轮播间隔时间
            }
        };
        handler.postDelayed(runnable, 3000); // 设置初始延迟时间

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 插入文字素材数据
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "SCENIC SPOTS\n" +
                "景区介绍");
        contentValues.put("content", "\\u3000\\u3000雷琼世界地质公园湛江园区湖光岩风景名胜区位于广东省湛江市"+"区西南部18公里处，总面积为37.1平方公里，是一个以玛珥火山地质地貌为主体，兼有海岸地貌、构造地质地貌等多种地质遗迹，自然生态良好，人文景观丰富的公园。");

        db.insert("scenic_spot", null, contentValues);

        scenicTitleTextView = findViewById(R.id.scenic_title);
        scenicContentTextView = findViewById(R.id.scenic_content);

        // 查询数据库获取文字素材数据
        Cursor cursor = db.rawQuery("SELECT * FROM scenic_spot WHERE id = 1", null);
        if (cursor.moveToFirst()) {
            scenicTitleTextView.setText(cursor.getString(cursor.getColumnIndex("title")));
            scenicContentTextView.setText(cursor.getString(cursor.getColumnIndex("content")));
        }

        cursor.close();
        db.close();

        // 初始化顶部RecyclerView
        topRecyclerView = findViewById(R.id.topRecyclerView);
        topLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topRecyclerView.setLayoutManager(topLinearLayoutManager);
        topImageResIds = new ArrayList<>();
        // 添加顶部图片资源ID到列表中
        topImageResIds.add(R.drawable.carousel_image1);
        topImageResIds.add(R.drawable.carousel_image2);
        topAdapter = new TopCarouselAdapter(this, topImageResIds);
        topRecyclerView.setAdapter(topAdapter);
        startTopAutoScroll();
        // 其他代码...


        // 初始化底部RecyclerView
        bottomRecyclerView = findViewById(R.id.bottomRecyclerView);
        bottomLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bottomRecyclerView.setLayoutManager(bottomLinearLayoutManager);
        bottomImageResIds = new ArrayList<>();
        // 添加底部图片资源ID到列表中
        bottomImageResIds.add(R.drawable.carousel_image3);
        bottomImageResIds.add(R.drawable.carousel_image4);
        bottomAdapter = new BottomCarouselAdapter(this, bottomImageResIds);
        bottomRecyclerView.setAdapter(bottomAdapter);
        startBottomAutoScroll();
    }
    private void startTopAutoScroll() {
        final Handler topHandler = new Handler();
        final Runnable topUpdate = new Runnable() {
            @Override
            public void run() {
                if (currentTopPosition == topImageResIds.size()) {
                    currentTopPosition = 0;
                }
                topRecyclerView.smoothScrollToPosition(currentTopPosition++);
            }
        };

        topTimer = new Timer();
        topTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                topHandler.post(topUpdate);
            }
        }, TOP_DELAY_MS, TOP_PERIOD_MS);
    }
    private void startBottomAutoScroll() {
        final Handler bottomHandler = new Handler();
        final Runnable bottomUpdate = new Runnable() {
            @Override
            public void run() {
                if (currentBottomPosition == bottomImageResIds.size()) {
                    currentBottomPosition = 0;
                }
                bottomRecyclerView.smoothScrollToPosition(currentBottomPosition++);
            }
        };

        bottomTimer = new Timer();
        bottomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                bottomHandler.post(bottomUpdate);
            }
        }, BOTTOM_DELAY_MS, BOTTOM_PERIOD_MS);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 移除轮播任务
        topTimer.cancel();
    }
}