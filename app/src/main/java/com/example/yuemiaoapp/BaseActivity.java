package com.example.yuemiaoapp;


import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yuemiaoapp.function.ActivityCollector;
import com.example.yuemiaoapp.function.ForceOffLineReceiver;

/*
* 该类作为所有活动的父类，用于回收
* */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    private ForceOffLineReceiver forceOffLineReceiver;
    /*
    * 用resume和Pause是为了让活动处于栈顶位置的时候才需要接收这条广播
    * */
    @Override
    protected void onResume() {
        //动态注册广播器
        super.onResume();
        IntentFilter filter = new IntentFilter();
        //增加需要监听的内容
        filter.addAction("com.example.myschool.FORCE_OFFLINE"); //监听强制下线广播
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE"); //监听网络连接广播
        forceOffLineReceiver = new ForceOffLineReceiver();
        //注册监听，这样ForceOfflineReceiver就可以接收到所有FORCE——OFFLINE的广播
        registerReceiver(forceOffLineReceiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(forceOffLineReceiver != null){
            unregisterReceiver(forceOffLineReceiver); //最后取消注册
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//回收所有活动
    }
}

