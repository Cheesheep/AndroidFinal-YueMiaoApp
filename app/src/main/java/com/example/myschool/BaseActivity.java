package com.example.myschool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myschool.function.ActivityCollector;

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
//用于广播下线
class ForceOffLineReceiver extends BroadcastReceiver {
    //该监听器包括了网络监听何强制下线监听
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//构建对话框
        builder.setTitle("警告");
        String action = intent.getAction();
        if (action.equals("com.example.myschool.FORCE_OFFLINE")) {
            // Handle the FORCE_OFFLINE broadcast
            builder.setMessage("你已经被强制下线，请重新登录！");
            builder.setCancelable(false);//讲对话框设置为不可取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();//校徽所有活动
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent); //重新启动LoginActivity
                }
            });
        }
        else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            // Handle the CONNECTIVITY_CHANGE broadcast
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable())
                return;
            else
                builder.setMessage("网络发生异常，请检查网络！");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
        builder.show();//令该对话框可视化
    }
}
