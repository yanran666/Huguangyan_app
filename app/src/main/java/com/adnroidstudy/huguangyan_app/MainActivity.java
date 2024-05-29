package com.adnroidstudy.huguangyan_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.adnroidstudy.huguangyan_app.adapter.MyPagerAdapter;
import com.adnroidstudy.huguangyan_app.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Integer> images = new ArrayList<>();
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private TextView scenicTitleTextView;
    private TextView scenicContentTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

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
        contentValues.put("content", "          雷琼世界地质公园湛江园区湖光岩风景名胜区位于广东省湛江市"+"区西南部18公里处，总面积为37.1平方公里，是一个以玛珥火山地质地貌为主体，兼有海岸地貌、构造地质地貌等多种地质遗迹，自然生态良好，人文景观丰富的公园。");

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
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 移除轮播任务
    }
}