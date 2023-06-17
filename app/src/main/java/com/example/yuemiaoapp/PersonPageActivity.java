package com.example.yuemiaoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuemiaoapp.function.BottomBarUtil;
import com.example.yuemiaoapp.function.ForceOffLineReceiver;
import com.example.yuemiaoapp.schobject.UserInfo;

public class PersonPageActivity extends BaseActivity {
    UserInfo curUserInfo = LoginActivity.userInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_page);
        setTitle("Personal Page");
        initUserInfo();
        //初始化底部bar
        Button button = findViewById(R.id.logout_button);
        button.setOnClickListener(view -> {
            //发送注销账户的广播
            ForceOffLineReceiver.sendLogoutBroadCast(this);
        });
        BottomBarUtil.initBottomBar(PersonPageActivity.this);
    }

    int[] imgArr = {R.drawable.admin,R.drawable.dog,R.drawable.mokelo};
    private void initUserInfo(){
        //初始化用户信息
        TextView textView = findViewById(R.id.person_nickname);
        textView.setText(curUserInfo.getNickname());
        //加载用户头像
        int imgUrl = Integer.parseInt(LoginActivity.userInfo.getImgUrl());
        ImageView imageView = findViewById(R.id.person_image);
        imageView.setImageResource(imgArr[imgUrl]);
        LinearLayout shouzhongzhe = findViewById(R.id.shouzhongzhe);
        shouzhongzhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InoculateListActivity.actionStart(PersonPageActivity.this);
            }
        });
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,PersonPageActivity.class);
        context.startActivity(intent);
    }
}