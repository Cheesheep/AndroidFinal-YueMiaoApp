package com.example.yuemiaoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * 该活动是主页跳转到《更多服务》后跳转过来的次级主页
 */
public class VaccineCardActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_card);
        ActionBar actionBar = getSupportActionBar();
        setTitle("SZU Menu");
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    //封装活动跳转的接口
    public static void actionStart(Context context){
        Intent intent = new Intent(context, VaccineCardActivity.class);
        context.startActivity(intent);
    }
}