package com.example.yuemiaoapp.personpage.inoculatepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuemiaoapp.common.BaseActivity;
import com.example.yuemiaoapp.login.LoginActivity;
import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.entity.InoculateInfo;
import com.example.yuemiaoapp.entity.UserInfo;

import org.litepal.LitePal;

public class InoculateInfoPageActivity extends BaseActivity {
    //获取当前的受种者信息
    InoculateInfo curInoculateInfo = InoculateListAdapter.card_inoculate_info;
    UserInfo curUserInfo = LoginActivity.userInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoculate_info_page);
        setTitle("User Info Page");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回图标可
            actionBar.setHomeButtonEnabled(true);//设置返回图标是否可以点击
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    @SuppressLint("SetTextI18n")
    private void initUserInfo(){
        //用户信息赋值
        TextView textView = findViewById(R.id.ino_name);
        textView.setText(curInoculateInfo.getName());
        textView = findViewById(R.id.ino_id_type);
        textView.setText(curInoculateInfo.getId_type());
        textView = findViewById(R.id.ino_identity);
        textView.setText(curInoculateInfo.getIdentity());
        textView = findViewById(R.id.ino_birth);
        textView.setText(curInoculateInfo.getBirth());
        textView = findViewById(R.id.ino_sex);
        textView.setText(curInoculateInfo.getSex());
        textView = findViewById(R.id.ino_phone);
        textView.setText(curInoculateInfo.getPhone());
        textView = findViewById(R.id.ino_location);
        textView.setText(curInoculateInfo.getLocation());
        textView = findViewById(R.id.ino_detail_loc);
        textView.setText(curInoculateInfo.getLoc_detail());
        //绑定受种者按钮
        Button bindButton = findViewById(R.id.ino_bind_button);
        if(curInoculateInfo.getId() == curUserInfo.getInoculate_id()){
            //若已绑定则让按钮变灰并且无点击事件
            bindButton.setBackgroundColor(getResources().getColor(R.color.gray));
            bindButton.setText("已经绑定");
        }else
            bindButton.setOnClickListener(view -> {
                //点击事件：将当前受种者和用户绑定
                UserInfo userInfo = LitePal.find(UserInfo.class,curUserInfo.getId());
                userInfo.setInoculate_id(curInoculateInfo.getId());
                userInfo.save();//更新当前用户绑定的受种者ID
                Log.d("用户INO: " + curUserInfo.getInoculate_id(),"INO: " + curInoculateInfo.getId());
                LoginActivity.userInfo = LitePal.find(UserInfo.class,curUserInfo.getId());//更新全局数据
                finish();//退回到上一个活动
            });
        //更新受种者信息按钮
        Button updateButton = findViewById(R.id.ino_update_button);
        updateButton.setOnClickListener(view -> {
            finish();//结束当前页面
            InoculateEditActivity.actionStart(this,"update");
        });

    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context, InoculateInfoPageActivity.class);
        context.startActivity(intent);
    }
}