package com.example.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myschool.schobject.UserInfo;

public class UserInfoPageActivity extends BaseActivity {
    UserInfo curUserInfo = LoginActivity.userInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_page);
        setTitle("User Info Page");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回图标可
            actionBar.setHomeButtonEnabled(true);//设置返回图标是否可以点击
        }
        initUserInfo();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//返回图标对应事件
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    int[] imgArr = {R.drawable.admin,R.drawable.dog,R.drawable.mokelo};
    @SuppressLint("SetTextI18n")
    private void initUserInfo(){
        //用户信息赋值
        TextView textView = findViewById(R.id.person_nickname);
        textView.setText(curUserInfo.getNickname());
        textView = findViewById(R.id.person_grade_major);
        textView.setText(curUserInfo.getGrade() + " " + curUserInfo.getMajor());
        textView = findViewById(R.id.person_number);
        textView.setText(curUserInfo.getAdmin());
        textView = findViewById(R.id.person_phone);
        textView.setText(curUserInfo.getPhone());
        textView = findViewById(R.id.person_sex);
        textView.setText(curUserInfo.getSex());
        int imgUrl = Integer.parseInt(LoginActivity.userInfo.getImgUrl());
        ImageView imageView = findViewById(R.id.person_image);
//        Drawable image = getResources().getDrawable( imgArr[imgUrl],getTheme());
//        imageView.setImageDrawable(image);
        imageView.setImageResource(imgArr[imgUrl]);
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,UserInfoPageActivity.class);
        context.startActivity(intent);
    }
}