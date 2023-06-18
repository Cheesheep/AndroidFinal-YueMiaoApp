package com.example.yuemiaoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.king.zxing.CaptureActivity;

/**
 * func: 团体预约界面（扫描二维码加入）
* */
public class TeamBookActivity extends AppCompatActivity {
    ImageView QRButton;
    EditText editText;
    public static final int REQUEST_CODE_SCAN = 0X02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_book);
        setTitle("Join Team");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            setTitle(getIntent().getStringExtra("title"));
            actionBar.setHomeButtonEnabled(true);//设置左上角是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);//添加返回的图标
        }

    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//设置返回按钮事件
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,TeamBookActivity.class);
        context.startActivity(intent);
    }
}