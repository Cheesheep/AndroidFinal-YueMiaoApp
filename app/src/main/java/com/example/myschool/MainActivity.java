package com.example.myschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myschool.schobject.UserInfo;

public class MainActivity extends BaseActivity {

    //主活动的功能比较少，因为功能都放在Fragment碎片当中
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //该方法用于接收来自Login登录时候传入的用户数据
    public static void actionStart(Context context, UserInfo userInfo){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("nickname",userInfo.getNickname());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("major",userInfo.getMajor());
        intent.putExtra("url",userInfo.getImgUrl());
        context.startActivity(intent);
    }
}
