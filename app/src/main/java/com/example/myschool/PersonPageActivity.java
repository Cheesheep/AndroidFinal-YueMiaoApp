package com.example.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myschool.function.BottomBarUtil;
import com.example.myschool.schobject.UserInfo;

public class PersonPageActivity extends AppCompatActivity {
    UserInfo curUserInfo = LoginActivity.userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_page);
        setTitle("Personal Page");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回图标可
            actionBar.setHomeButtonEnabled(true);//设置返回图标是否可以点击
        }
        initUserInfo();
        //初始化底部bar
        BottomBarUtil.initBottomBar(PersonPageActivity.this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//返回图标对应事件
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initUserInfo(){
        //TODO 初始化用户信息
        TextView textView = findViewById(R.id.person_nickname);
        textView.setText(curUserInfo.getNickname());
        LinearLayout shouzhongzhe = findViewById(R.id.shouzhongzhe);
        shouzhongzhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoPageActivity.actionStart(PersonPageActivity.this);
            }
        });
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,PersonPageActivity.class);
        context.startActivity(intent);
    }
}